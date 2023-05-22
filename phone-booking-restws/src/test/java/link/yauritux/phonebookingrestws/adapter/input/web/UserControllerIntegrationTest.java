package link.yauritux.phonebookingrestws.adapter.input.web;

import link.yauritux.phonebookingrestws.config.IntegrationTestConfiguration;
import link.yauritux.port.input.dto.request.UserRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class UserControllerIntegrationTest extends IntegrationTestConfiguration {

    @Test
    void registerUser() {
        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new UserRegistrationRequest("yaurigneel")), UserRegistrationRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name")
                .isEqualTo("yaurigneel");
    }
}

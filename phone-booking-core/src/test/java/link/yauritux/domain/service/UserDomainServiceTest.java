package link.yauritux.domain.service;

import link.yauritux.domain.entity.User;
import link.yauritux.port.input.dto.request.UserRegistrationRequest;
import link.yauritux.port.output.repository.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class UserDomainServiceTest {

    private UserDomainService sut;

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @BeforeEach
    void setUp() {
        sut = new UserDomainService(userRepositoryPort);
    }

    @Test
    void registerUser_nullUsername_gotException() {
        var response = sut.registerUser(new UserRegistrationRequest(null));

        StepVerifier.create(response)
                .expectErrorMessage("name must not be empty!")
                .verify();
    }

    @Test
    void registerUser_emptyUsername_gotException() {
        var response = sut.registerUser(new UserRegistrationRequest(" "));

        StepVerifier.create(response)
                .expectErrorMessage("name must not be empty!")
                .verify();
    }

    @Test
    void registerUser_ok() {
        var createdUser = User.builder().id(UUID.randomUUID().toString()).name("yaurigneel").build();
        when(userRepositoryPort.save(any(User.class)))
                .thenReturn(Mono.just(createdUser));

        var response = sut.registerUser(new UserRegistrationRequest("yaurigneel"));

        StepVerifier.create(response)
                .consumeNextWith(r -> {
                    assert r != null;
                    assert r.id() != null;
                    assertEquals("yaurigneel", r.name());
                }).verifyComplete();
    }

    @Test
    void findById_userNotFound() {
        var id = UUID.randomUUID().toString();
        when(userRepositoryPort.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(sut.findById(id)).expectNextCount(0).verifyComplete();
    }

    @Test
    void findById_userFound() {
        var id = UUID.randomUUID().toString();
        var user = Mono.just(User.builder().id(id).name("yauritux").build());
        when(userRepositoryPort.findById(id)).thenReturn(user);

        StepVerifier.create(sut.findById(id)).expectNextCount(1).verifyComplete();
    }

    @Test
    void findAll() {
        var records = Flux.fromIterable(List.of(
                User.builder().id(UUID.randomUUID().toString()).name("yauritux").build(),
                User.builder().id(UUID.randomUUID().toString()).name("igneel").build()
        ));
        when(userRepositoryPort.findAll(0, 10)).thenReturn(records);

        StepVerifier.create(sut.fetchAllUsers(0, 10))
                .expectNextCount(2).verifyComplete();
    }
}
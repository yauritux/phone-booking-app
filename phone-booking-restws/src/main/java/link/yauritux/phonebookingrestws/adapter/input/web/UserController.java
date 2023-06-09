package link.yauritux.phonebookingrestws.adapter.input.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import link.yauritux.port.input.dto.request.UserRegistrationRequest;
import link.yauritux.port.input.dto.response.UserRegistrationResponse;
import link.yauritux.port.input.service.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@RestController
@Tag(name = "User Services", description = "User REST API Controller")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserServicePort userServicePort;

    @PostMapping
    public Mono<ResponseEntity<UserRegistrationResponse>> registerUser(@RequestBody UserRegistrationRequest request) {
        return userServicePort.registerUser(request)
                .map(r -> ResponseEntity.status(HttpStatus.CREATED).body(r))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new UserRegistrationResponse(null, null, e.getMessage()))));
    }

    @GetMapping
    public Flux<UserRegistrationResponse> fetchAllUsers(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        return userServicePort.fetchAllUsers(page, limit)
                .map(r -> new UserRegistrationResponse(r.getId(), r.getName()));
    }
}

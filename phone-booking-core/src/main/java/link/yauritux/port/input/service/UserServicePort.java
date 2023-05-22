package link.yauritux.port.input.service;

import link.yauritux.domain.entity.User;
import link.yauritux.port.input.dto.request.UserRegistrationRequest;
import link.yauritux.port.input.dto.response.UserRegistrationResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public interface UserServicePort {

    /**
     * Used to register a new user into the system.
     * @see link.yauritux.port.input.dto.request.UserRegistrationRequest
     * @see link.yauritux.port.input.dto.response.UserRegistrationResponse
     *
     * @param request
     * @return UserRegistrationResponse
     */
    Mono<UserRegistrationResponse> registerUser(UserRegistrationRequest request);

    /**
     * Used to find a user by its' id.
     *
     * @param id id of the user
     * @return User
     */
    Mono<User> findById(String id);

    Flux<User> fetchAllUsers(int page, int limit);
}

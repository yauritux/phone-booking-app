package link.yauritux.domain.service;

import link.yauritux.domain.entity.User;
import link.yauritux.exception.UserRegistrationFailedException;
import link.yauritux.port.input.dto.request.UserRegistrationRequest;
import link.yauritux.port.input.dto.response.UserRegistrationResponse;
import link.yauritux.port.input.service.UserServicePort;
import link.yauritux.port.output.repository.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * User core domain service that contains all necessary business logic specific to a User
 * such as: registering a user, searching for a user, etc.
 *
 * @see link.yauritux.port.output.repository.UserRepositoryPort
 */
@RequiredArgsConstructor
public class UserDomainService implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public Mono<UserRegistrationResponse> registerUser(UserRegistrationRequest request) {
        var newUser = User.builder().name(request.name()).build();
        try {
            newUser.validateEntity();
        } catch (UserRegistrationFailedException e) {
            return Mono.error(e);
        }
        return userRepositoryPort.save(newUser)
                .map(r -> new UserRegistrationResponse(r.getId(), r.getName())
                );
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepositoryPort.findById(id);
    }

    @Override
    public Flux<User> fetchAllUsers(int page, int limit) {
        return userRepositoryPort.findAll(page, limit);
    }
}

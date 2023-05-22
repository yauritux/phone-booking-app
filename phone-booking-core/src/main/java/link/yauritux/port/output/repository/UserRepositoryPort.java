package link.yauritux.port.output.repository;

import link.yauritux.domain.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Contract for the user repository that can be connected to a specific repository infrastructure
 * (e.g., database, cache, file, etc).
 * @see link.yauritux.domain.entity.User
 */
public interface UserRepositoryPort {

    /**
     * Saving a user into the repository system (e.g., database, cache, file, etc).
     *
     * @param user
     * @return User
     */
    Mono<User> save(User user);

    /**
     * Find a user based on their ID as registered in the system.
     *
     * @param id user unique identifier
     * @return User
     */
    Mono<User> findById(String id);

    /**
     * Fetch all registered users based on given page number and number of records per-page.
     *
     * @param page represents page number. Number starts with 0 for page 1, 1 for page 2 and so forth.
     * @param limit number of records to be displayed per-page.
     * @return
     */
    Flux<User> findAll(int page, int limit);
}

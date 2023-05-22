package link.yauritux.port.output.repository;

import link.yauritux.domain.entity.Phone;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Contract for the phone repository that can be connected to a specific repository infrastructure
 * (e.g., database, cache, file, etc).
 * @see link.yauritux.domain.entity.Phone
 */
public interface PhoneRepositoryPort {

    /**
     * Register a phone into the repository system (e.g., database, cache, file, etc).
     *
     * @param phone
     * @return Phone
     */
    Mono<Phone> save(Phone phone);

    /**
     * Find a phone based on its' ID as registered in the system.
     *
     * @param id phone unique identifier
     * @return Phone
     */
    Mono<Phone> findById(String id);

    /**
     * Updating a phone data.
     *
     * @param phone
     * @return Phone
     */
    Mono<Phone> update(Phone phone);

    /**
     * Fetch all registered phones based on the given page number and number of records per-page.
     *
     * @param page represents page number. Number starts with 0 for page 1, 1 for page 2 and so forth.
     * @param limit number of records to be displayed per-page.
     * @return
     */
    Flux<Phone> findAll(int page, int limit);
}

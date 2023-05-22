package link.yauritux.port.output.repository;

import link.yauritux.domain.entity.BookingEntry;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Contract for the booking entry repository that can be connected to a specific repository infrastructure
 * (e.g., database, cache, file, etc).
 * @see link.yauritux.domain.entity.BookingEntry
 */
public interface BookingEntryRepositoryPort {

    /**
     * Adding a new line for a booking entity into the repository system (e.g., database, cache, file, etc).
     *
     * @param entry a bookingEntry object that is going to be persisted into the repository.
     * @return BookingEntry
     */
    Mono<BookingEntry> save(BookingEntry entry);

    /**
     * Find a booking entry line based on its' ID as registered in the system.
     *
     * @param id booking id unique identifier
     * @return BookingEntry
     */
    Mono<BookingEntry> findById(String id);

    /**
     * Fetch a phone latest booking information from the booking entry lines.
     *
     * @param phoneId id of the phone
     * @return BookingEntry
     */
    Mono<BookingEntry> getPhoneLatestBookingInformation(String phoneId);
}

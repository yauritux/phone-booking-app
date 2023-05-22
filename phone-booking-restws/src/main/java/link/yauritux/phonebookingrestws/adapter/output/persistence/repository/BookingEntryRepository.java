package link.yauritux.phonebookingrestws.adapter.output.persistence.repository;

import link.yauritux.domain.entity.BookingEntry;
import link.yauritux.phonebookingrestws.adapter.output.persistence.mapper.BookingEntryDocumentEntityMapper;
import link.yauritux.port.output.repository.BookingEntryRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * An adapter class used to perform any CRUD operation against our mongo database after some processes completed
 * by the booking entity domain service in the core domain layer.
 */
@RequiredArgsConstructor
public class BookingEntryRepository implements BookingEntryRepositoryPort {

    private final BookingEntryDocumentRepository bookingEntryDocumentRepository;

    @Override
    public Mono<BookingEntry> save(BookingEntry entry) {
        return bookingEntryDocumentRepository.save(BookingEntryDocumentEntityMapper.INSTANCE.entityToDocument(entry))
                .map(BookingEntryDocumentEntityMapper.INSTANCE::documentToEntity);
    }

    @Override
    public Mono<BookingEntry> findById(String id) {
        return bookingEntryDocumentRepository.findById(id)
                .map(BookingEntryDocumentEntityMapper.INSTANCE::documentToEntity);
    }

    @Override
    public Mono<BookingEntry> getPhoneLatestBookingInformation(String phoneId) {
        return bookingEntryDocumentRepository.findBookingEntryDocumentsByPhoneIdAndStatusOrderByTimestampDesc(
                phoneId, BookingEntry.BookingStatus.BOOKED).map(
                BookingEntryDocumentEntityMapper.INSTANCE::documentToEntity).next();
    }
}

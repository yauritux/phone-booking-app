package link.yauritux.phonebookingrestws.adapter.output.persistence.repository;

import link.yauritux.domain.entity.BookingEntry;
import link.yauritux.phonebookingrestws.adapter.output.persistence.document.BookingEntryDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@Repository
public interface BookingEntryDocumentRepository extends ReactiveMongoRepository<BookingEntryDocument, String> {

    Flux<BookingEntryDocument> findBookingEntryDocumentsByPhoneIdAndStatusOrderByTimestampDesc(
            String phoneId, BookingEntry.BookingStatus status);
}

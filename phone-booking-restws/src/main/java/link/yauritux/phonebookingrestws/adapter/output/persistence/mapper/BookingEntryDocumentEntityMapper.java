package link.yauritux.phonebookingrestws.adapter.output.persistence.mapper;

import link.yauritux.domain.entity.BookingEntry;
import link.yauritux.phonebookingrestws.adapter.output.persistence.document.BookingEntryDocument;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * A mapper class to convert a booking entry document collection to a booking entry domain entity, and vice versa.
 *
 * @see link.yauritux.phonebookingrestws.adapter.output.persistence.document.BookingEntryDocument
 * @see link.yauritux.domain.entity.BookingEntry
 */
public class BookingEntryDocumentEntityMapper {

    public static final BookingEntryDocumentEntityMapper INSTANCE = new BookingEntryDocumentEntityMapper();

    private BookingEntryDocumentEntityMapper() {}

    /**
     * Convert booking entry entity to its corresponding booking entry document collection in mongo.
     * This is used as a necessary step before saving a booking entry record as a collection in our mongo database.
     *
     * @param bookingEntry a booking entry entity as defined within the core domain
     * @return BookingEntryDocument
     */
    public BookingEntryDocument entityToDocument(BookingEntry bookingEntry) {
        return BookingEntryDocument.builder()
                .timestamp(bookingEntry.getTimestamp())
                .phoneId(bookingEntry.getPhoneId())
                .userId(bookingEntry.getUserId())
                .status(bookingEntry.getStatus())
                .build();
    }

    /**
     * Convert a booking entry document to its corresponding booking entry entity.
     * This is used after we fetched a booking entry collection from mongo and proceed with further logics
     * defined in our core domain layer.
     *
     * @param bookingEntryDocument a booking entry document that represents a booking entry collection in mongo database.
     * @return BookingEntry
     */
    public BookingEntry documentToEntity(BookingEntryDocument bookingEntryDocument) {
        return BookingEntry.builder()
                .id(bookingEntryDocument.getId())
                .timestamp(bookingEntryDocument.getTimestamp())
                .phoneId(bookingEntryDocument.getPhoneId())
                .userId(bookingEntryDocument.getUserId())
                .status(bookingEntryDocument.getStatus())
                .build();
    }
}

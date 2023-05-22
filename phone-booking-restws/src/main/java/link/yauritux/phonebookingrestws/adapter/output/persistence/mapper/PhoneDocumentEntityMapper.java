package link.yauritux.phonebookingrestws.adapter.output.persistence.mapper;

import link.yauritux.domain.entity.Phone;
import link.yauritux.phonebookingrestws.adapter.output.persistence.document.PhoneDocument;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * A mapper class to convert a phone document collection to a phone domain entity, and vice versa.
 *
 * @see link.yauritux.phonebookingrestws.adapter.output.persistence.document.PhoneDocument
 * @see link.yauritux.domain.entity.Phone
 */
public class PhoneDocumentEntityMapper {

    public static final PhoneDocumentEntityMapper INSTANCE = new PhoneDocumentEntityMapper();

    private PhoneDocumentEntityMapper() {}

    /**
     * Convert phone entity to its corresponding phone document collection in mongo.
     * This is used as a necessary step before saving a phone record as a collection in our mongo database.
     *
     * @param phone a phone entity as defined within the core domain
     * @return PhoneDocument
     */
    public PhoneDocument entityToDocument(Phone phone) {
        return PhoneDocument.builder()
                .brand(phone.getBrand())
                .deviceName(phone.getDeviceName())
                .technology(phone.getTechnology())
                ._2gBands(phone.get_2gBands())
                ._3gBands(phone.get_3gBands())
                ._4gBands(phone.get_4gBands())
                .available(phone.isAvailable())
                .build();
    }

    /**
     * Convert a phone document to its corresponding phone entity.
     * This is used after we fetched a phone collection from mongo and proceed with further logics
     * defined in our core domain layer.
     *
     * @param phoneDocument a phone document that represents a phone collection in mongo database.
     * @return Phone
     */
    public Phone phoneDocumentToPhoneEntity(PhoneDocument phoneDocument) {
        return Phone.builder()
                .id(phoneDocument.getId())
                .brand(phoneDocument.getBrand())
                .deviceName(phoneDocument.getDeviceName())
                .technology(phoneDocument.getTechnology())
                ._2gBands(phoneDocument.get_2gBands())
                ._3gBands(phoneDocument.get_3gBands())
                ._4gBands(phoneDocument.get_4gBands())
                .available(phoneDocument.isAvailable())
                .build();
    }
}

package link.yauritux.phonebookingrestws.adapter.output.persistence.mapper;

import link.yauritux.domain.entity.User;
import link.yauritux.phonebookingrestws.adapter.output.persistence.document.UserDocument;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * A mapper class to convert a user document collection to a user domain entity, and vice versa.
 *
 * @see link.yauritux.phonebookingrestws.adapter.output.persistence.document.UserDocument
 * @see link.yauritux.domain.entity.User
 */
public class UserDocumentEntityMapper {

    public static final UserDocumentEntityMapper INSTANCE = new UserDocumentEntityMapper();

    private UserDocumentEntityMapper() {}

    /**
     * Convert user entity to its corresponding user document collection in mongo.
     * This is used as a necessary step before saving a user record as a collection in our mongo database.
     *
     * @param user a user entity as defined within the core domain
     * @return UserDocument
     */
    public UserDocument entityToDocument(User user) {
        return UserDocument.builder()
                .name(user.getName())
                .build();
    }

    /**
     * Convert a user document to its corresponding user entity.
     * This is used after we fetched a user collection from mongo and proceed with further logics
     * defined in our core domain layer.
     *
     * @param userDocument a user document that represents a user collection in mongo database.
     * @return User
     */
    public User documentToEntity(UserDocument userDocument) {
        return User.builder()
                .id(userDocument.getId())
                .name(userDocument.getName())
                .build();
    }
}

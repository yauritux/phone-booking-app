package link.yauritux.restfulapi.adapter.output.persistence.repository;

import link.yauritux.phonebookingrestws.adapter.output.persistence.document.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@Repository
public interface UserDocumentRepository extends ReactiveMongoRepository<UserDocument, String> {
}

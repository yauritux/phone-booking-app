package link.yauritux.phonebookingrestws.adapter.output.persistence.repository;

import link.yauritux.phonebookingrestws.adapter.output.persistence.document.PhoneDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@Repository
public interface PhoneDocumentRepository extends ReactiveMongoRepository<PhoneDocument, String> {

    Flux<PhoneDocument> findAllBy(Pageable pageable);
}

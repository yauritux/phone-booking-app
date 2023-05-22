package link.yauritux.phonebookingrestws.adapter.output.persistence.repository;

import link.yauritux.domain.entity.Phone;
import link.yauritux.phonebookingrestws.adapter.output.persistence.mapper.PhoneDocumentEntityMapper;
import link.yauritux.port.output.repository.PhoneRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * An adapter class used to perform any CRUD operation against our mongo database after some processes completed
 * by the phone domain service in the core domain layer.
 */
@RequiredArgsConstructor
public class PhoneRepository implements PhoneRepositoryPort {

    private final PhoneDocumentRepository phoneDocumentRepository;

    @Override
    public Mono<Phone> save(Phone phone) {
        return phoneDocumentRepository.save(PhoneDocumentEntityMapper.INSTANCE.entityToDocument(phone))
                .map(PhoneDocumentEntityMapper.INSTANCE::documentToEntity);
    }

    @Override
    public Mono<Phone> findById(String id) {
        return phoneDocumentRepository.findById(id)
                .map(PhoneDocumentEntityMapper.INSTANCE::documentToEntity);
    }

    // TODO:: must implement
    @Override
    public Mono<Phone> update(Phone phone) {
        return null;
    }
}

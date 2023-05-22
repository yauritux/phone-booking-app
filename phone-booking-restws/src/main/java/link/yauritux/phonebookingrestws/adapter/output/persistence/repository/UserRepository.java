package link.yauritux.phonebookingrestws.adapter.output.persistence.repository;

import link.yauritux.domain.entity.User;
import link.yauritux.phonebookingrestws.adapter.output.persistence.mapper.UserDocumentEntityMapper;
import link.yauritux.port.output.repository.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * An adapter class used to perform any CRUD operation against our mongo database after some processes completed
 * by the user domain service in the core domain layer.
 *
 */
@RequiredArgsConstructor
public class UserRepository implements UserRepositoryPort {

    private final UserDocumentRepository userDocumentRepository;

    @Override
    public Mono<User> save(User user) {
        return userDocumentRepository.save(UserDocumentEntityMapper.INSTANCE.entityToDocument(user))
                .map(UserDocumentEntityMapper.INSTANCE::documentToEntity);
    }

    @Override
    public Mono<User> findById(String id) {
        return userDocumentRepository.findById(id).map(UserDocumentEntityMapper.INSTANCE::documentToEntity);
    }
}

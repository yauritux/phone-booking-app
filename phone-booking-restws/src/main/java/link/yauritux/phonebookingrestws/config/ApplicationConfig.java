package link.yauritux.phonebookingrestws.config;

import link.yauritux.domain.service.UserDomainService;
import link.yauritux.phonebookingrestws.adapter.output.persistence.repository.UserDocumentRepository;
import link.yauritux.phonebookingrestws.adapter.output.persistence.repository.UserRepository;
import link.yauritux.port.input.service.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserDocumentRepository userDocumentRepository;

    @Bean
    UserRepository userRepository() {
        return new UserRepository(userDocumentRepository);
    }

    @Bean
    UserServicePort userServicePort() {
        return new UserDomainService(userRepository());
    }
}

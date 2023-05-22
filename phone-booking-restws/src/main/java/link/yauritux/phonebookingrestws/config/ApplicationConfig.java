package link.yauritux.phonebookingrestws.config;

import link.yauritux.domain.service.PhoneDomainService;
import link.yauritux.domain.service.UserDomainService;
import link.yauritux.phonebookingrestws.adapter.output.persistence.repository.PhoneDocumentRepository;
import link.yauritux.phonebookingrestws.adapter.output.persistence.repository.PhoneRepository;
import link.yauritux.phonebookingrestws.adapter.output.persistence.repository.UserDocumentRepository;
import link.yauritux.phonebookingrestws.adapter.output.persistence.repository.UserRepository;
import link.yauritux.phonebookingrestws.adapter.output.webclient.FonoApiClient;
import link.yauritux.port.input.service.PhoneServicePort;
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

    private final PhoneDocumentRepository phoneDocumentRepository;

    private final FonoApiClient fonoApiClient;

    @Bean
    UserRepository userRepository() {
        return new UserRepository(userDocumentRepository);
    }

    @Bean
    UserServicePort userServicePort() {
        return new UserDomainService(userRepository());
    }

    @Bean
    PhoneRepository phoneRepository() { return new PhoneRepository(phoneDocumentRepository); }

    @Bean
    PhoneServicePort phoneServicePort() { return new PhoneDomainService(phoneRepository(), fonoApiClient); }
}

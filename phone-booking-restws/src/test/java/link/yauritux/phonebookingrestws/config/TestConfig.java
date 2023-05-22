package link.yauritux.phonebookingrestws.config;

import link.yauritux.phonebookingrestws.adapter.output.persistence.repository.UserDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("test")
@RequiredArgsConstructor
public class TestConfig {

    private final UserDocumentRepository userDocumentRepository;
}

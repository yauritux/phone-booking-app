package link.yauritux.phonebookingrestws.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@Testcontainers
@AutoConfigureWebTestClient(timeout = "3600000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestConfiguration {

    @Autowired
    protected WebTestClient webTestClient;

    protected final static MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:5.0.12")
                    .withReuse(true);

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    protected static void dynamicPropertiesProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.default.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.default.mongodb.database", mongoDBContainer::getHost);
        registry.add("spring.data.default.mongodb.port", mongoDBContainer::getHost);
    }
}

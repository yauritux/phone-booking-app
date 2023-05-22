package link.yauritux.phonebookingrestws.adapter.output.persistence.repository;

import link.yauritux.phonebookingrestws.adapter.output.persistence.document.UserDocument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@Testcontainers
class UserDocumentRepositoryTest {

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5.0.12"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () -> mongoDBContainer.getReplicaSetUrl("embedded"));
    }

    @AfterEach
    void cleanUp() {
        userDocumentRepository.deleteAll().subscribe();
    }

    @Test
    void shouldSaveUser() {
        var response = userDocumentRepository.save(UserDocument.builder().name("yauritux").build());
        StepVerifier.create(response)
                .consumeNextWith(r -> {
                    assert r != null;
                    assert r.getId() != null;
                    assertEquals("yauritux", r.getName());
                }).verifyComplete();
    }
}
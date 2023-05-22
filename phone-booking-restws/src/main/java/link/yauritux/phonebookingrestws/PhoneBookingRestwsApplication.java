package link.yauritux.phonebookingrestws;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.annotation.PostConstruct;
import link.yauritux.phonebookingrestws.adapter.output.persistence.document.PhoneDocument;
import link.yauritux.phonebookingrestws.adapter.output.persistence.repository.BookingEntryDocumentRepository;
import link.yauritux.phonebookingrestws.adapter.output.persistence.repository.PhoneDocumentRepository;
import link.yauritux.port.output.repository.PhoneSpecRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@SpringBootApplication
@EnableWebFlux
@OpenAPIDefinition(info = @Info(
        title = "PhoneBooking API Specs",
        version = "1.0",
        description = "PhoneBooking API Documentations v1.0",
        contact = @Contact(name = "Yauri Attamimi", email = "yauritux@gmail.com")))
@EnableReactiveMongoRepositories
public class PhoneBookingRestwsApplication {

    @Autowired
    @Qualifier("fonoApiClient")
    private PhoneSpecRepositoryPort fonoApiClient;

    @Autowired
    private PhoneDocumentRepository phoneDocumentRepository;

    @Autowired
    private BookingEntryDocumentRepository bookingEntryDocumentRepository;

    public static void main(String[] args) {
        SpringApplication.run(PhoneBookingRestwsApplication.class, args);
    }

    /**
     * Please think again about using this kind of post-construct in production!!!
     * This is used for development only
     */
    @PostConstruct
    public void init() {
        bookingEntryDocumentRepository.deleteAll().subscribe();

        phoneDocumentRepository.deleteAll().subscribe();

        Flux<String> brands = Flux.fromIterable(List.of("Samsung", "Samsung", "Motorola", "Oneplus", "Apple",
                "Apple", "Apple", "Apple", "Nokia"));
        Flux<String> models = Flux.fromIterable(List.of("Samsung Galaxy S9", "2x Samsung Galaxy S8",
                "Motorola Nexus 6", "Oneplus 9", "Apple iPhone 13", "Apple iPhone 12", "Apple iPhone 11",
                "iPhone X", "Nokia 3310"));

        brands.zipWith(models).subscribe(p -> fonoApiClient.fetchPhoneSpecification(
                p.getT1(), p.getT2()).subscribe(r -> {
            var phone = PhoneDocument.builder()
                    .brand(r.brand())
                    .deviceName(r.deviceName())
                    .technology(r.technology())
                    ._2gBands(r._2gBands())
                    ._3gBands(r._3gBands())
                    ._4gBands(r._4gBands())
                    .available(true)
                    .build();
            phoneDocumentRepository.save(phone).subscribe();
        }));
    }

}

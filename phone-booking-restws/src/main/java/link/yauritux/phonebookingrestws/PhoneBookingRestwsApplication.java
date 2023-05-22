package link.yauritux.phonebookingrestws;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

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
public class PhoneBookingRestwsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneBookingRestwsApplication.class, args);
    }

}

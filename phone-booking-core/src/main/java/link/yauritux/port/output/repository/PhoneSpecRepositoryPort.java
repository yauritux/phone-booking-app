package link.yauritux.port.output.repository;

import link.yauritux.port.output.dto.response.PhoneSpecificationResponse;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * A contract interface to get phone specification details from the upstream service (e.g., FONOAPI).
 * We can switch to other upstream services, yet any of the adapters that's going to consume those upstream services
 * should implement this contract as our standard.
 *
 * @see link.yauritux.port.output.dto.response.PhoneSpecificationResponse
 */
public interface PhoneSpecRepositoryPort {

    /**
     * fetch a phone specification details based on given brand and deviceName or model.
     *
     * @param brand brand of the phone (e.g., Samsung, Apple, Motorola)
     * @param deviceName device model name (e.g., Apple iPhone 13, Samsung Galaxy S9)
     * @return
     */
    Mono<PhoneSpecificationResponse> fetchPhoneSpecification(String brand, String deviceName);
}

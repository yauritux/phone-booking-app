package link.yauritux.port.input.service;

import link.yauritux.domain.entity.Phone;
import link.yauritux.port.input.dto.request.PhoneRegistrationRequest;
import link.yauritux.port.input.dto.response.PhoneRegistrationResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * @see link.yauritux.port.input.dto.request.PhoneRegistrationRequest
 * @see link.yauritux.port.input.dto.response.PhoneRegistrationResponse
 * @see link.yauritux.domain.entity.Phone
 */
public interface PhoneServicePort {

    /**
     * Registering a phone into the system.
     *
     * @param request payload contains phone detailed information
     * @return PhoneRegistrationResponse
     */
    Mono<PhoneRegistrationResponse> registerPhone(PhoneRegistrationRequest request);

    /**
     * Retrieve phone by its' id
     *
     * @param id
     * @return Phone
     */
    Mono<Phone> getPhoneById(String id);

    /**
     * Used to update phone availability status.
     *
     * @param id phone's id as registered in the system.
     * @param available a boolean (true|false) to indicates phones' availability.
     *      <code>true</code> means phone is available while <code>false</code> means phone is not available.
     * @return
     */
    Mono<Phone> updatePhoneAvailability(String id, boolean available);

    /**
     * Fetch all registered phones based on given page number and number of record per-page.
     *
     * @param page represents page number
     * @param limit number of records to be displayed per-page.
     * @return
     */
    Flux<Phone> fetchAllPhones(int page, int limit);
}

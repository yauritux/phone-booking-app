package link.yauritux.domain.service;

import link.yauritux.domain.entity.Phone;
import link.yauritux.exception.PhoneNotFoundException;
import link.yauritux.exception.PhoneRegistrationFailedException;
import link.yauritux.port.input.dto.request.PhoneRegistrationRequest;
import link.yauritux.port.input.dto.response.PhoneRegistrationResponse;
import link.yauritux.port.input.service.PhoneServicePort;
import link.yauritux.port.output.repository.PhoneRepositoryPort;
import link.yauritux.port.output.repository.PhoneSpecRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Phone core domain service that contains all necessary business logic specific to a Phone
 * such as: registering a phone, searching for a phone, update phone data etc.
 */
@RequiredArgsConstructor
public class PhoneDomainService implements PhoneServicePort {

    private final PhoneRepositoryPort phoneRepositoryPort;

    private final PhoneSpecRepositoryPort phoneSpecRepositoryPort;

    @Override
    public Mono<PhoneRegistrationResponse> registerPhone(PhoneRegistrationRequest request) {
        var newPhone = Phone.builder()
                .brand(request.brand()).deviceName(request.deviceName())
                .available(true) // phone will be available at first time registered
                .build();
        try {
            newPhone.validateEntity();
        } catch (PhoneRegistrationFailedException e) {
            return Mono.error(e);
        }
        return phoneSpecRepositoryPort.fetchPhoneSpecification(request.brand(), request.deviceName())
                .flatMap(phoneSpec -> {
                    newPhone.setTechnology(phoneSpec.technology());
                    newPhone.set_2gBands(phoneSpec._2gBands());
                    newPhone.set_3gBands(phoneSpec._3gBands());
                    newPhone.set_4gBands(phoneSpec._4gBands());
                    return persistPhone(newPhone);
                }).switchIfEmpty(persistPhone(newPhone));
    }

    @Override
    public Mono<Phone> getPhoneById(String id) {
        return phoneRepositoryPort.findById(id);
    }

    @Override
    public Mono<Phone> updatePhoneAvailability(String id, boolean available) {
        return phoneRepositoryPort.findById(id)
                .map(p -> {
                    p.setAvailable(available);
                    phoneRepositoryPort.save(p).subscribeOn(Schedulers.boundedElastic()).subscribe();
                    return p;
                }).switchIfEmpty(Mono.error(new PhoneNotFoundException(
                        String.format("Cannot find phone with id %s!", id)
                )));
    }

    @Override
    public Flux<Phone> fetchAllPhones(int page, int limit) {
        return phoneRepositoryPort.findAll(page, limit);
    }

    private Mono<PhoneRegistrationResponse> persistPhone(Phone phone) {
        return phoneRepositoryPort.save(phone)
                .map(r -> PhoneRegistrationResponse.builder()
                        .id(r.getId())
                        .brand(r.getBrand())
                        .deviceName(r.getDeviceName())
                        .technology(r.getTechnology())
                        ._2gBands(r.get_2gBands())
                        ._3gBands(r.get_3gBands())
                        ._4gBands(r.get_4gBands())
                        .build()
                );
    }
}

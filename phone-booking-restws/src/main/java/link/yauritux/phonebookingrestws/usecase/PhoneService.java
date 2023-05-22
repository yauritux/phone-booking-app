package link.yauritux.phonebookingrestws.usecase;

import link.yauritux.domain.entity.Phone;
import link.yauritux.exception.DataNotFoundException;
import link.yauritux.phonebookingrestws.usecase.dto.PhoneSummaryResponse;
import link.yauritux.port.input.service.PhoneServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class PhoneService {

    private final BookingPhoneService bookingPhoneService;

    private final PhoneServicePort phoneServicePort;

    public Mono<PhoneSummaryResponse> getPhoneDetailsInformation(String phoneId) {
        return bookingPhoneService.getPhoneLatestBookingInformation(phoneId)
                .flatMap(rec ->
                        Mono.just(rec).zipWith(phoneServicePort.getPhoneById(phoneId))
                )
                .map(tuple ->
                        PhoneSummaryResponse.builder()
                                .phoneId(tuple.getT2().getId())
                                .brand(tuple.getT2().getBrand())
                                .deviceName(tuple.getT2().getDeviceName())
                                .technology(tuple.getT2().getTechnology())
                                ._2gBands(tuple.getT2().get_2gBands())
                                ._3gBands(tuple.getT2().get_3gBands())
                                ._4gBands(tuple.getT2().get_4gBands())
                                .isAvailable(tuple.getT2().isAvailable() ? "Yes" : "No")
                                .latestBookedOn(tuple.getT1().getBookedOn())
                                .latestBookedBy(tuple.getT1().getUserName())
                                .build()
                )
                .switchIfEmpty(Mono.error(
                        new DataNotFoundException(
                                String.format("Cannog find phone with id %s!", phoneId)
                        )
                ));
    }

    public Flux<PhoneSummaryResponse> fetchAllPhones(int page, int limit) {
        var phoneRecords = phoneServicePort.fetchAllPhones(page, limit);
        return phoneRecords
                .flatMap(r ->
                    Mono.just(r).zipWith(bookingPhoneService.getPhoneLatestBookingInformation(r.getId()))
                )
                .map(tuple -> buildResponse(tuple.getT1(), tuple.getT2().getBookedOn(), tuple.getT2().getUserName())
                )
                .mergeWith(phoneRecords.map(r -> buildResponse(r, null, null)))
                .switchIfEmpty(phoneRecords.map(r -> buildResponse(r, null, null)));
    }

    private PhoneSummaryResponse buildResponse(Phone phone, LocalDateTime latestBookedOn, String latestBookedBy) {
        return PhoneSummaryResponse.builder()
                .phoneId(phone.getId())
                .brand(phone.getBrand())
                .deviceName(phone.getDeviceName())
                .technology(phone.getTechnology())
                ._2gBands(phone.get_2gBands())
                ._3gBands(phone.get_3gBands())
                ._4gBands(phone.get_4gBands())
                .isAvailable(phone.isAvailable() ? "Yes" : "No")
                .latestBookedOn(latestBookedOn)
                .latestBookedBy(latestBookedBy)
                .build();
    }
}

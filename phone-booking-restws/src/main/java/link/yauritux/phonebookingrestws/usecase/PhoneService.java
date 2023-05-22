package link.yauritux.phonebookingrestws.usecase;

import link.yauritux.exception.DataNotFoundException;
import link.yauritux.phonebookingrestws.usecase.dto.PhoneSummaryResponse;
import link.yauritux.port.input.service.PhoneServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
}

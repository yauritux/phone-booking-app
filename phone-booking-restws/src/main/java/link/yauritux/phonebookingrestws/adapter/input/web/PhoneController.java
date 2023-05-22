package link.yauritux.phonebookingrestws.adapter.input.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import link.yauritux.phonebookingrestws.usecase.BookingPhoneService;
import link.yauritux.port.input.dto.request.BookingEntryRequest;
import link.yauritux.port.input.dto.request.PhoneRegistrationRequest;
import link.yauritux.port.input.dto.response.BookingEntryResponse;
import link.yauritux.port.input.dto.response.PhoneRegistrationResponse;
import link.yauritux.port.input.service.PhoneServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@RestController
@Tag(name = "Phone Services", description = "Phone REST API Controller")
@RequestMapping("/phones")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneServicePort phoneServicePort;

    private final BookingPhoneService bookingPhoneService;

    @PostMapping
    public Mono<ResponseEntity<PhoneRegistrationResponse>> registerPhone(@RequestBody PhoneRegistrationRequest request) {
        return phoneServicePort.registerPhone(request)
                .map(r -> ResponseEntity.status(HttpStatus.CREATED).body(r))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(PhoneRegistrationResponse.builder().errorMessage(e.getMessage()).build())));
    }

    @PostMapping("/booking")
    public Mono<ResponseEntity<BookingEntryResponse>> bookingPhone(@RequestBody BookingEntryRequest request) {
        return bookingPhoneService.bookingPhone(request).map(r ->
                ResponseEntity.status(HttpStatus.CREATED).body(r)
        ).onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BookingEntryResponse.builder().errorMessage(e.getMessage()).build())));
    }
}

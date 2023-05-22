package link.yauritux.port.input.service;

import link.yauritux.domain.entity.BookingEntry;
import link.yauritux.port.input.dto.request.BookingEntryRequest;
import link.yauritux.port.input.dto.response.BookingEntryResponse;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public interface BookingEntryServicePort {

    Mono<BookingEntryResponse> bookingPhone(BookingEntryRequest request);
    Mono<BookingEntryResponse> returnPhone(String bookingId);

    Mono<BookingEntryResponse> getPhoneLatestBookingInformation(String phoneId);
}

package link.yauritux.domain.service;

import link.yauritux.domain.entity.BookingEntry;
import link.yauritux.exception.BookingFailedException;
import link.yauritux.exception.DataNotFoundException;
import link.yauritux.exception.PhoneNotAvailableException;
import link.yauritux.port.input.dto.request.BookingEntryRequest;
import link.yauritux.port.input.dto.response.BookingEntryResponse;
import link.yauritux.port.input.service.BookingEntryServicePort;
import link.yauritux.port.input.service.PhoneServicePort;
import link.yauritux.port.input.service.UserServicePort;
import link.yauritux.port.output.repository.BookingEntryRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Booking entry core domain service that contains all necessary business logic specific to booking a phone transaction.
 */
@RequiredArgsConstructor
public class BookingEntryDomainService implements BookingEntryServicePort {

    private final BookingEntryRepositoryPort bookingEntryRepositoryPort;

    private final PhoneServicePort phoneServicePort;

    private final UserServicePort userServicePort;

    @Override
    public Mono<BookingEntryResponse> bookingPhone(BookingEntryRequest request) {
        if (request.phoneId() == null || request.phoneId().trim().equals("")) {
            return Mono.error(new BookingFailedException("Please provide the `phoneId`!"));
        }
        if (request.userId() == null || request.userId().trim().equals("")) {
            return Mono.error(new BookingFailedException("Please provide the `userId`!"));
        }
        return phoneServicePort.getPhoneById(request.phoneId())
                .flatMap(phone -> Mono.just(phone).zipWith(userServicePort.findById(request.userId()))
                ).flatMap(result -> {
                    if (!result.getT1().isAvailable()) {
                        return Mono.error(new PhoneNotAvailableException(
                                String.format("Sorry, %s is not available at the moment!",
                                        result.getT1().getDeviceName())
                        ));
                    }
                    var newEntry = BookingEntry.builder()
                            .phoneId(result.getT1().getId())
                            .userId(result.getT2().getId())
                            .timestamp(LocalDateTime.now())
                            .status(BookingEntry.BookingStatus.BOOKED)
                            .build();
                    return bookingEntryRepositoryPort.save(newEntry)
                            .map(r -> BookingEntryResponse.builder()
                                    .id(r.getId())
                                    .bookedOn(r.getTimestamp())
                                    .phoneId(r.getPhoneId())
                                    .deviceName(result.getT1().getDeviceName())
                                    .technology(result.getT1().getTechnology())
                                    ._2gBands(result.getT1().get_2gBands())
                                    ._3gBands(result.getT1().get_3gBands())
                                    ._4gBands(result.getT1().get_4gBands())
                                    .userId(r.getUserId())
                                    .userName(result.getT2().getName())
                                    .status(r.getStatus()).build());
                }).switchIfEmpty(Mono.error(new DataNotFoundException(
                        "Either `phoneId` or `userId` cannot be found!")
                ));
    }

    @Override
    public Mono<BookingEntryResponse> returnPhone(String bookingId) {
        return bookingEntryRepositoryPort.findById(bookingId)
                .flatMap(rec ->
                        Mono.just(rec).zipWith(phoneServicePort.getPhoneById(rec.getPhoneId()))
                                .map(result -> {
                                    rec.setStatus(BookingEntry.BookingStatus.RETURNED);
                                    rec.setTimestamp(LocalDateTime.now());
                                    bookingEntryRepositoryPort.save(rec).subscribe();
                                    return Tuples.of(rec, result.getT2());
                                })
                                .map(tuple ->
                                        BookingEntryResponse.builder()
                                                .id(tuple.getT1().getId())
                                                .returnedOn(tuple.getT1().getTimestamp())
                                                .phoneId(tuple.getT1().getPhoneId())
                                                .deviceName(tuple.getT2().getDeviceName())
                                                .technology(tuple.getT2().getTechnology())
                                                ._2gBands(tuple.getT2().get_2gBands())
                                                ._3gBands(tuple.getT2().get_3gBands())
                                                ._4gBands(tuple.getT2().get_4gBands())
                                                .userId(tuple.getT1().getUserId())
                                                .status(tuple.getT1().getStatus())
                                                .build()
                                )
                                .flatMap(er ->
                                        Mono.just(er).zipWith(userServicePort.findById(er.getUserId()))
                                                .map(t -> {
                                                    t.getT1().setUserName(t.getT2().getName());
                                                    return t.getT1();
                                                })
                                )
                )
                .switchIfEmpty(Mono.error(
                        new DataNotFoundException(String.format("Cannot find booking with id %s", bookingId))
                ));
    }

    @Override
    public Mono<BookingEntryResponse> getPhoneLatestBookingInformation(String phoneId) {
        return bookingEntryRepositoryPort.getPhoneLatestBookingInformation(phoneId)
                .map(entry ->
                        BookingEntryResponse.builder()
                                .id(entry.getId())
                                .phoneId(entry.getPhoneId())
                                .userId(entry.getUserId())
                                .bookedOn(entry.getTimestamp())
                                .status(entry.getStatus())
                                .build()
                )
                .flatMap(resp ->
                        Mono.just(resp).zipWith(phoneServicePort.getPhoneById(resp.getPhoneId()))
                )
                .map(t -> {
                    t.getT1().setDeviceName(t.getT2().getDeviceName());
                    t.getT1().setTechnology(t.getT2().getTechnology());
                    t.getT1().set_2gBands(t.getT2().get_2gBands());
                    t.getT1().set_3gBands(t.getT2().get_3gBands());
                    t.getT1().set_4gBands(t.getT2().get_4gBands());
                    return t.getT1();
                })
                .flatMap(er ->
                        Mono.just(er).zipWith(userServicePort.findById(er.getUserId()))
                )
                .map(t2 -> {
                    t2.getT1().setUserName(t2.getT2().getName());
                    return t2.getT1();
                });
    }
}

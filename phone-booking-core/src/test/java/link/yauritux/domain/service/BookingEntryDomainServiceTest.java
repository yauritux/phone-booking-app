package link.yauritux.domain.service;

import link.yauritux.domain.entity.BookingEntry;
import link.yauritux.domain.entity.Phone;
import link.yauritux.domain.entity.User;
import link.yauritux.exception.BookingFailedException;
import link.yauritux.exception.DataNotFoundException;
import link.yauritux.exception.PhoneNotAvailableException;
import link.yauritux.port.input.dto.request.BookingEntryRequest;
import link.yauritux.port.input.service.PhoneServicePort;
import link.yauritux.port.input.service.UserServicePort;
import link.yauritux.port.output.repository.BookingEntryRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class BookingEntryDomainServiceTest {

    private BookingEntryDomainService sut;

    @Mock
    private BookingEntryRepositoryPort bookingEntryRepositoryPort;

    @Mock
    private PhoneServicePort phoneServicePort;

    @Mock
    private UserServicePort userServicePort;

    @BeforeEach
    void setUp() {
        sut = new BookingEntryDomainService(bookingEntryRepositoryPort, phoneServicePort, userServicePort);
    }

    @Test
    void bookingPhone_phoneIsIsNull_gotBookingFailedMessage() {
        var response = sut.bookingPhone(new BookingEntryRequest(null, "yauritux"));
        StepVerifier.create(response)
                .expectErrorMessage("Please provide the `phoneId`!").verify();
    }

    @Test
    void bookingPhone_phoneIdIsEmpty_gotBookingFailedException() {
        var response = sut.bookingPhone(new BookingEntryRequest(" ", "yauritux"));
        StepVerifier.create(response)
                .expectError(BookingFailedException.class)
                .verify();
    }

    @Test
    void bookingPhone_userIdIsNull_gotBookingFailedMessage() {
        var response = sut.bookingPhone(
                new BookingEntryRequest(UUID.randomUUID().toString(), null));
        StepVerifier.create(response)
                .expectErrorMessage("Please provide the `userId`!")
                .verify();
    }

    @Test
    void bookingPhone_userIdIsEmpty_gotBookingFailedException() {
        var response = sut.bookingPhone(new BookingEntryRequest(
                UUID.randomUUID().toString(), " "));
        StepVerifier.create(response).expectError(BookingFailedException.class).verify();
    }

    @Test
    void bookingPhone_phoneNotFound_gotDataNotFoundException() {
        var id = UUID.randomUUID().toString();
        when(phoneServicePort.getPhoneById(id)).thenReturn(Mono.empty());
        var response = sut.bookingPhone(new BookingEntryRequest(id, "yauritux"));
        StepVerifier.create(response).expectError(DataNotFoundException.class).verify();
    }

    @Test
    void bookingPhone_phoneNotAvailable_gotPhoneNotAvailableException() {
        var id = UUID.randomUUID().toString();
        var userId = UUID.randomUUID().toString();
        var savedPhone = Phone.builder()
                .id(id)
                .brand("Apple iPhone")
                .deviceName("Apple iPhone13 Pro")
                .technology("GSM / CDMA / HSPA / EVDO / LTE / 5G")
                ._2gBands("GSM 850 / 900 / 1800 / 1900 - SIM 1 & SIM 2 (dual-SIM) CDMA 800 / 1900")
                ._3gBands("HSDPA 850 / 900 / 1700(AWS) / 1900 / 2100 CDMA2000 1xEV-DO")
                .available(false)
                .build();
        when(phoneServicePort.getPhoneById(id)).thenReturn(Mono.just(savedPhone));
        when(userServicePort.findById(userId)).thenReturn(
                Mono.just(User.builder().id(userId).name("yauritux").build()));

        var response = sut.bookingPhone(new BookingEntryRequest(id, userId));
        StepVerifier.create(response)
                .expectError(PhoneNotAvailableException.class).verify();
    }

    @Test
    void bookingPhone_success() {
        var id = UUID.randomUUID().toString();
        var userId = UUID.randomUUID().toString();

        var savedPhone = Phone.builder()
                .id(id)
                .brand("Apple iPhone")
                .deviceName("Apple iPhone13 Pro")
                .technology("GSM / CDMA / HSPA / EVDO / LTE / 5G")
                ._2gBands("GSM 850 / 900 / 1800 / 1900 - SIM 1 & SIM 2 (dual-SIM) CDMA 800 / 1900")
                ._3gBands("HSDPA 850 / 900 / 1700(AWS) / 1900 / 2100 CDMA2000 1xEV-DO")
                .available(true)
                .build();

        var request = new BookingEntryRequest(id, userId);

        var newEntry = spy(BookingEntry.builder()
                .id(UUID.randomUUID().toString())
                .phoneId(savedPhone.getId())
                .timestamp(LocalDateTime.now())
                .status(BookingEntry.BookingStatus.BOOKED)
                .userId(request.userId())
                .build());

        when(phoneServicePort.getPhoneById(id)).thenReturn(Mono.just(savedPhone));
        when(userServicePort.findById(request.userId())).thenReturn(
                Mono.just(User.builder().id(userId).name("yauritux").build()));
        when(bookingEntryRepositoryPort.save(any(BookingEntry.class))).thenReturn(Mono.just(newEntry));

        var response = sut.bookingPhone(request);
        StepVerifier.create(response)
                .consumeNextWith(r -> {
                    assert r != null;
                    assert r.getId() != null;
                    assert r.getBookedOn() != null;
                    assert r.getStatus().equals(BookingEntry.BookingStatus.BOOKED);
                    verify(newEntry, atLeastOnce()).getId();
                    verify(newEntry, atLeastOnce()).getTimestamp();
                    verify(newEntry, atLeastOnce()).getPhoneId();
                    verify(newEntry, atLeastOnce()).getUserId();
                    verify(newEntry, atLeastOnce()).getStatus();
                }).verifyComplete();
    }
}
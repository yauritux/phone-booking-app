package link.yauritux.domain.service;

import link.yauritux.domain.entity.Phone;
import link.yauritux.exception.PhoneNotFoundException;
import link.yauritux.port.input.dto.request.PhoneRegistrationRequest;
import link.yauritux.port.output.dto.response.PhoneSpecificationResponse;
import link.yauritux.port.output.repository.PhoneRepositoryPort;
import link.yauritux.port.output.repository.PhoneSpecRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class PhoneDomainServiceTest {

    private PhoneDomainService sut;

    @Mock
    private PhoneRepositoryPort phoneRepositoryPort;

    @Mock
    private PhoneSpecRepositoryPort phoneSpecRepositoryPort;

    @BeforeEach
    void setUp() {
        sut = new PhoneDomainService(phoneRepositoryPort, phoneSpecRepositoryPort);
    }

    @Test
    void registerPhone_nullBrand_gotException() {
        var response = sut.registerPhone(
                new PhoneRegistrationRequest(null, null));

        StepVerifier.create(response)
                .expectErrorMessage("brand must not be empty!")
                .verify();
    }

    @Test
    void registerPhone_emptyBrand_gotException() {
        var response = sut.registerPhone(
                new PhoneRegistrationRequest(" ", null));

        StepVerifier.create(response)
                .expectErrorMessage("brand must not be empty!")
                .verify();
    }

    @Test
    void registerPhone_nullDeviceName_gotException() {
        var response = sut.registerPhone(
                new PhoneRegistrationRequest("Apple iPhone", null));

        StepVerifier.create(response)
                .expectErrorMessage("device name must not be empty!")
                .verify();
    }

    @Test
    void registerPhone_emptyDeviceName_gotException() {
        var response = sut.registerPhone(
                new PhoneRegistrationRequest("Apple iPhone", " "));

        StepVerifier.create(response)
                .expectErrorMessage("device name must not be empty!")
                .verify();
    }

    @Test
    void registerPhone_noResponseFromPhoneSpecApi_phoneRegisteredWithNoDetailSpec() {
        var savedPhone = Phone.builder()
                .id(UUID.randomUUID().toString())
                .brand("Apple iPhone")
                .deviceName("Apple iPhone13 Pro")
                .build();
        when(phoneSpecRepositoryPort.fetchPhoneSpecification("Apple iPhone", "Apple iPhone13 Pro"))
                .thenReturn(Mono.empty());

        when(phoneRepositoryPort.save(any(Phone.class))).thenReturn(Mono.just(savedPhone));

        var response = sut.registerPhone(
                new PhoneRegistrationRequest("Apple iPhone", "Apple iPhone13 Pro"));

        StepVerifier.create(response)
                .consumeNextWith(r -> {
                    assert r != null;
                    assert r.getId() != null;
                    assertEquals("Apple iPhone", r.getBrand());
                    assertEquals("Apple iPhone13 Pro", r.getDeviceName());
                    assertNull(r.get_2gBands());
                }).verifyComplete();
    }

    @Test
    void registerPhone_gotResponseFromPhoneSpecApi_phoneRegisteredWithDetailSpec() {
        var savedPhone = Phone.builder()
                .id(UUID.randomUUID().toString())
                .brand("Apple iPhone")
                .deviceName("Apple iPhone13 Pro")
                .technology("GSM / CDMA / HSPA / EVDO / LTE / 5G")
                ._2gBands("GSM 850 / 900 / 1800 / 1900 - SIM 1 & SIM 2 (dual-SIM) CDMA 800 / 1900")
                ._3gBands("HSDPA 850 / 900 / 1700(AWS) / 1900 / 2100 CDMA2000 1xEV-DO")
                .build();

        var phoneSpec = new PhoneSpecificationResponse(savedPhone.getBrand(), savedPhone.getDeviceName(),
                savedPhone.getTechnology(), savedPhone.get_2gBands(), savedPhone.get_3gBands(),
                savedPhone.get_4gBands());

        when(phoneSpecRepositoryPort.fetchPhoneSpecification("Apple iPhone", "Apple iPhone13 Pro"))
                .thenReturn(Mono.just(phoneSpec));

        when(phoneRepositoryPort.save(any(Phone.class))).thenReturn(Mono.just(savedPhone));

        var response = sut.registerPhone(
                new PhoneRegistrationRequest("Apple iPhone", "Apple iPhone13 Pro"));

        StepVerifier.create(response)
                .consumeNextWith(r -> {
                    assert r != null;
                    assert r.getId() != null;
                    assertEquals("Apple iPhone", r.getBrand());
                    assertEquals("Apple iPhone13 Pro", r.getDeviceName());
                    assertEquals(savedPhone.getTechnology(), r.getTechnology());
                    assertNotNull(r.get_2gBands());
                    assertNotNull(r.get_3gBands());
                    assertNull(r.get_4gBands());
                }).verifyComplete();
    }

    @Test
    void getPhoneById_noDataFound() {
        var id = "646a5b0e2fe3ea7ab6104cd0";
        when(phoneRepositoryPort.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(sut.getPhoneById(id)).expectNextCount(0).verifyComplete();
    }

    @Test
    void getPhoneById_dataFound() {
        var savedPhone = Phone.builder()
                .id(UUID.randomUUID().toString())
                .brand("Apple iPhone")
                .deviceName("Apple iPhone13 Pro")
                .technology("GSM / CDMA / HSPA / EVDO / LTE / 5G")
                ._2gBands("GSM 850 / 900 / 1800 / 1900 - SIM 1 & SIM 2 (dual-SIM) CDMA 800 / 1900")
                ._3gBands("HSDPA 850 / 900 / 1700(AWS) / 1900 / 2100 CDMA2000 1xEV-DO")
                .build();
        var id = UUID.randomUUID().toString();
        when(phoneRepositoryPort.findById(id)).thenReturn(Mono.just(savedPhone));

        StepVerifier.create(sut.getPhoneById(id)).expectNextCount(1).verifyComplete();
    }

    @Test
    void updatePhoneAvailability_phoneNotFound_gotPhoneNotFoundException() {
        var id = UUID.randomUUID().toString();
        when(phoneRepositoryPort.findById(id)).thenReturn(Mono.empty());

        var response = sut.updatePhoneAvailability(id, true);
        StepVerifier.create(response)
                .expectError(PhoneNotFoundException.class)
                .verify();
    }
}
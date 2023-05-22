package link.yauritux.phonebookingrestws.adapter.output.webclient;

import link.yauritux.phonebookingrestws.config.FonoApiClientConfiguration;
import link.yauritux.port.output.dto.response.PhoneSpecificationResponse;
import link.yauritux.port.output.repository.PhoneSpecRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class FonoApiClient implements PhoneSpecRepositoryPort {

    private final FonoApiClientConfiguration fonoApiClientConfig;

    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    private final FonoApiFallbackService fallbackService;

    @Override
    public Mono<PhoneSpecificationResponse> fetchPhoneSpecification(String brand, String deviceName) {
        var fonoapiClient = fonoApiClientConfig.fonoapiWebClient();
        var results = fonoapiClient.get().uri(
                "getdevice?brand=" + brand + "&device=" + deviceName + "&position=0"
        ).retrieve().bodyToMono(PhoneSpecificationResponse.class);
        return reactiveCircuitBreaker.run(results, throwable -> fallbackResponse(deviceName));
    }

    private Mono<PhoneSpecificationResponse> fallbackResponse(String deviceName) {
        if (fallbackService.getLocalStorages().containsKey(deviceName)) {
            return Mono.just(fallbackService.getLocalStorages().get(deviceName));
        }
        return Mono.empty();
    }
}

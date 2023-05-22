package link.yauritux.phonebookingrestws.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@Configuration
public class FonoApiClientConfiguration {

    @Value("${fonoapi.url}")
    private String url;

    @Value("${FONOAPI_TOKEN}")
    @Getter private String token;

    @Value("${FONOAPI_TIMEOUT_MS}")
    private int timeoutMillisecond;

    @Bean
    public ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory() {
        var reactiveResilience4JCircuitBreakerFactory = new ReactiveResilience4JCircuitBreakerFactory();
        reactiveResilience4JCircuitBreakerFactory.configureDefault(buildConfiguration());
        return reactiveResilience4JCircuitBreakerFactory;
    }

    @Bean
    public ReactiveCircuitBreaker reactiveCircuitBreaker(ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory) {
        return reactiveCircuitBreakerFactory.create("fonoapiService");
    }

    private Function<String, Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration> buildConfiguration() {
        Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration resilience4JCircuitBreakerConfiguration = new Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration();
        CircuitBreakerConfig breakerConfig = CircuitBreakerConfig
                .from(CircuitBreakerConfig.ofDefaults())
                .build();
        resilience4JCircuitBreakerConfiguration.setCircuitBreakerConfig(breakerConfig);
        resilience4JCircuitBreakerConfiguration.setTimeLimiterConfig(TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(timeoutMillisecond)).build());
        return (id -> resilience4JCircuitBreakerConfiguration);
    }

    public WebClient fonoapiWebClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeoutMillisecond)
                .responseTimeout(Duration.ofMillis(timeoutMillisecond))
                .doOnConnected(conn -> conn.addHandlerLast(
                        new ReadTimeoutHandler(timeoutMillisecond, TimeUnit.MILLISECONDS)
                ).addHandlerLast(new WriteTimeoutHandler(timeoutMillisecond, TimeUnit.MILLISECONDS)));
        return WebClient.builder()
                .baseUrl(url)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
                })
                .build();
    }
}

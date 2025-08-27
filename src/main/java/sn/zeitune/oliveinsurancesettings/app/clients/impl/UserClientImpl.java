package sn.zeitune.oliveinsurancesettings.app.clients.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sn.zeitune.oliveinsurancesettings.app.clients.UserClient;

@Component
@Slf4j
public class UserClientImpl implements UserClient {


    private final WebClient userWebClient;

    public UserClientImpl(WebClient.Builder webClientBuilder,
          @Value("${services.olive-insurance-auth-service.base-url}")
          String authServiceUrl
    ) {
        this.userWebClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }


    private Mono<Throwable> handleError(ClientResponse response) {
        return response.bodyToMono(String.class).flatMap(errorBody -> {
            log.error("❗ Error response from user-service: status={} body={}", response.statusCode(), errorBody);
            return Mono.error(new RuntimeException("User service call failed: " + errorBody));
        });
    }
}

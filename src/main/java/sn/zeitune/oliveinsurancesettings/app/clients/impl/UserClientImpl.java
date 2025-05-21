package sn.zeitune.olive_insurance_administration.app.clients.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sn.zeitune.olive_insurance_administration.app.clients.UserClient;
import sn.zeitune.olive_insurance_administration.app.dto.external.CreateUserRequest;

@Component
@Slf4j
public class UserClientImpl implements UserClient {

    private final WebClient userWebClient;

    public UserClientImpl(WebClient.Builder webClientBuilder) {
        this.userWebClient = webClientBuilder.baseUrl("http://localhost:8010/api/v1").build();
    }

    @Override
    @Transactional
    public void createUser(CreateUserRequest request) {
            userWebClient.post()
                    .uri("/interservices/users")
                    .bodyValue(request)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleError)
                    .toBodilessEntity()
                    .block();

            log.info("✅ User created for company: {}", request.email());
    }

    private Mono<Throwable> handleError(ClientResponse response) {
        return response.bodyToMono(String.class).flatMap(errorBody -> {
            log.error("❗ Error response from user-service: status={} body={}", response.statusCode(), errorBody);
            return Mono.error(new RuntimeException("User service call failed: " + errorBody));
        });
    }
}

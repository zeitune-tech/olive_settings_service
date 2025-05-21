package sn.zeitune.oliveinsurancesettings.app.clients.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class AdministrationClientImpl  implements AdministrationClient {

    private final WebClient administrationWebClient;

    public AdministrationClientImpl(WebClient.Builder webClientBuilder) {
        this.administrationWebClient = webClientBuilder.baseUrl("http://localhost:8020/api/v1").build();
    }



    private Mono<Throwable> handleError(ClientResponse response) {
        return response.bodyToMono(String.class).flatMap(errorBody -> {
            log.error("❗ Error response from user-service: status={} body={}", response.statusCode(), errorBody);
            return Mono.error(new RuntimeException("User service call failed: " + errorBody));
        });
    }

    @Override
    public List<ProductResponseDTO> getByManagementEntity(UUID uuid) {
        return administrationWebClient.get()
                .uri("/interservices/products/{uuid}", uuid)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToFlux(ProductResponseDTO.class)
                .collectList()
                .block();
    }
}
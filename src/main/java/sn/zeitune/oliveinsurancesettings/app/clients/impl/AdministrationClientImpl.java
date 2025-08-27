package sn.zeitune.oliveinsurancesettings.app.clients.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
@Slf4j
public class AdministrationClientImpl  implements AdministrationClient {


    private final WebClient administrationWebClient;

    public AdministrationClientImpl(
            WebClient.Builder webClientBuilder,
            @Value("${services.olive-insurance-administration-service.base-url}") String administrationServiceUrl
    ) {
        this.administrationWebClient = webClientBuilder.baseUrl(administrationServiceUrl).build();
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

    @Override
    public List<ManagementEntityResponse> getManagementEntities(List<UUID> uuids) {
        return administrationWebClient.post()
                .uri("/interservices/management-entities")
                .bodyValue(uuids)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToFlux(ManagementEntityResponse.class)
                .collectList()
                .block();
    }

    @Override
    public List<ProductResponseDTO> getProductsByIds(List<UUID> uuids) {
        return administrationWebClient.post()
                .uri("/interservices/products")
                .bodyValue(uuids)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToFlux(ProductResponseDTO.class)
                .collectList()
                .block();
    }

    @Override
    public Optional<ManagementEntityResponse> findManagementEntityByUuid(UUID ownerUuid) {
        return administrationWebClient.get()
                .uri("/interservices/management-entities/{uuid}", ownerUuid)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToMono(ManagementEntityResponse.class)
                .blockOptional();
    }

    @Override
    public List<ManagementEntityResponse> findManagementEntityByUuidIn(Set<UUID> companyUuids) {
        return administrationWebClient.post()
                .uri("/interservices/management-entities/find-by-uuids")
                .bodyValue(companyUuids)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToFlux(ManagementEntityResponse.class)
                .collectList()
                .block();
    }
}
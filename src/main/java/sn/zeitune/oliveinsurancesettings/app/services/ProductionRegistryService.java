package sn.zeitune.oliveinsurancesettings.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductionRegistryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductionRegistryResponse;

import java.util.List;
import java.util.UUID;

public interface ProductionRegistryService {

    ProductionRegistryResponse create(ProductionRegistryRequest request, UUID managementEntity);

    ProductionRegistryResponse getByUuid(UUID uuid);

    Page<ProductionRegistryResponse> getAll(UUID managementEntity, Pageable pageable);
    List<ProductionRegistryResponse> getAll(UUID managementEntity);

    ProductionRegistryResponse update(UUID uuid, ProductionRegistryRequest request);

    void delete(UUID uuid);
}

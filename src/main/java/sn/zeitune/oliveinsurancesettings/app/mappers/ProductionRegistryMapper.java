package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductionRegistryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductionRegistryResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.ProductionRegistry;

public class ProductionRegistryMapper {

    public static ProductionRegistry map(ProductionRegistryRequest request) {
        return ProductionRegistry.builder()
                .prefix(request.prefix())
                .length(request.length())
                .counter(0)
                .build();
    }

    public static ProductionRegistryResponse map(ProductionRegistry registry, ProductResponseDTO product) {
        return ProductionRegistryResponse.builder()
                .id(registry.getUuid())
                .prefix(registry.getPrefix())
                .length(registry.getLength())
                .managementEntity(registry.getManagementEntity())
                .product(product)
                .counter(registry.getCounter())
                .build();
    }
}

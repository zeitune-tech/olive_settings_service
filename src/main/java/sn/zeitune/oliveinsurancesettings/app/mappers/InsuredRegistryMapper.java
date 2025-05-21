package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.InsuredRegistryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.InsuredRegistryResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.InsuredRegistry;

public class InsuredRegistryMapper {

    public static InsuredRegistry map(InsuredRegistryRequest request) {
        return InsuredRegistry.builder()
                .prefix(request.prefix())
                .length(request.length())
                .counter(0)
                .build();
    }

    public static InsuredRegistryResponse map(InsuredRegistry registry) {
        return InsuredRegistryResponse.builder()
                .id(registry.getUuid())
                .prefix(registry.getPrefix())
                .length(registry.getLength())
                .managementEntity(registry.getManagementEntity())
                .counter(registry.getCounter())
                .build();
    }
}

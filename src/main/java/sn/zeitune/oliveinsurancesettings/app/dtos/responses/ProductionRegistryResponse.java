package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductionRegistryResponse(
        UUID id,
        String prefix,
        int length,
        UUID managementEntity,
        ProductResponse product,
        int counter
) {}

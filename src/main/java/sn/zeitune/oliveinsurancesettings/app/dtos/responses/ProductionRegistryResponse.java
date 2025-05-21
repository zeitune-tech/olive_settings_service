package sn.zeitune.oliveinsurancesettings.app.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductionRegistryResponse(

        Long id,
        UUID uuid,
        String prefix,
        int length,
        UUID managementEntity

) {}

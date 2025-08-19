package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record VehicleUsageResponseDTO(
        UUID id,
        String name
) {
}


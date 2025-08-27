package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record VehicleBrandResponseDTO (
        UUID id,
        String name
) {}


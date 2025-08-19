package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;

import java.util.Set;
import java.util.UUID;

@Builder
public record VehicleCategoryResponseDTO(
        UUID id,
        String name,
        Boolean withTrailer,
        Boolean withChassis,
        Set<VehicleUsageResponseDTO> usages,
        Set<ProductResponse> products
) {}


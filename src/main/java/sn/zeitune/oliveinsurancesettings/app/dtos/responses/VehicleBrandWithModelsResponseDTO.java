package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.app.mappers.VehicleModelMapper;

import java.util.List;
import java.util.UUID;

@Builder
public record VehicleBrandWithModelsResponseDTO(
        UUID id,
        String name,
        List<VehicleModelResponseDTO> models
) {}


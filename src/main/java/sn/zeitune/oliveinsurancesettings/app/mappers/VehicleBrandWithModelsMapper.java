package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandWithModelsResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Brand;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Model;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleBrandWithModelsMapper {

    public static VehicleBrandWithModelsResponseDTO map(Brand vehicleBrand, List<Model> models) {
        if (vehicleBrand == null) return null;

        return VehicleBrandWithModelsResponseDTO.builder()
                .id(vehicleBrand.getUuid())
                .name(vehicleBrand.getName())
                .models(models.stream().map(VehicleModelMapper::map).collect(Collectors.toList()))
                .build();
    }
}

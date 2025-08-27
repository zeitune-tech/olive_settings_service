package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleUsageRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleUsageResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.VehicleUsage;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Brand;

public class VehicleUsageMapper {

    public static void put(VehicleUsage vehicleUsage, VehicleUsageRequestDTO vehicleUsageRequestDTO) {
        if (vehicleUsageRequestDTO == null || vehicleUsage == null) return;
        vehicleUsage.setName(vehicleUsageRequestDTO.name());
    }
    public static VehicleUsageResponseDTO map(VehicleUsage vehicleUsage) {
        if (vehicleUsage == null) return null;

        return VehicleUsageResponseDTO.builder()
                .id(vehicleUsage.getUuid())
                .name(vehicleUsage.getName())
                .build();
    }
}

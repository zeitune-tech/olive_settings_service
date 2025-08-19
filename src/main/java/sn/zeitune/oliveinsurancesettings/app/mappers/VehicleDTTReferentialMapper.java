package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleDTTReferentialResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.DTTReferential;

public class VehicleDTTReferentialMapper {

    public static VehicleDTTReferentialResponseDTO map(DTTReferential DTTVehicle) {
        if (DTTVehicle == null) return null;

        return VehicleDTTReferentialResponseDTO.builder()
                .id(DTTVehicle.getUuid())
                .name(DTTVehicle.getModel().getBrand().getName())
                .model(VehicleModelMapper.map(DTTVehicle.getModel()))
                .registrationNumber(DTTVehicle.getRegistrationNumber())
                .build();
    }
}

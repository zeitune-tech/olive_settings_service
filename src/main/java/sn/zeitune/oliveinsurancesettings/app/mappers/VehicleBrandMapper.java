package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleDTTReferentialResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Brand;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.DTTReferential;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Model;

import java.util.List;

public class VehicleBrandMapper {

    public static VehicleBrandResponseDTO map(Brand vehicleBrand) {
        if (vehicleBrand == null) return null;

        return VehicleBrandResponseDTO.builder()
                .id(vehicleBrand.getUuid())
                .name(vehicleBrand.getName())
                .build();
    }
}

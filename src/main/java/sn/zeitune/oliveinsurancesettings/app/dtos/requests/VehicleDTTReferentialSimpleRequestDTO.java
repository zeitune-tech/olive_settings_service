package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import lombok.Builder;

@Builder
public record VehicleDTTReferentialSimpleRequestDTO(
        String brandName,

        String modelName,

        String registrationNumber
    ) {

}

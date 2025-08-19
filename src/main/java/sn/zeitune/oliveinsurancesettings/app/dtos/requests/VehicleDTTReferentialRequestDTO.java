package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import java.util.UUID;

public record VehicleDTTReferentialRequestDTO(
        UUID brandId,

        UUID modelId,

        String registrationNumber
    ) {

}

package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.BodyWorkType;
import sn.zeitune.oliveinsurancesettings.enums.MotorizationType;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record VehicleDTTReferentialResponseDTO(
        UUID id,

        String name,

        VehicleModelResponseDTO model,

        String registrationNumber
) {
}


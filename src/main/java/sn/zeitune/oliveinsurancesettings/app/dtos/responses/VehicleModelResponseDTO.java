package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.BodyWorkType;
import sn.zeitune.oliveinsurancesettings.enums.MotorizationType;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record VehicleModelResponseDTO(
        UUID id,

        String name,

        String motorizationType,

        String bodywork,

        Integer placeCount,

        Boolean hasTurbo,

        BigDecimal horsepower,

        BigDecimal displacement, // cylindrée en litre

        BigDecimal weight, // poids en kilogramme

        String nature // Nature du véhicule (ex: particulier, utilitaire, etc.
) {
}


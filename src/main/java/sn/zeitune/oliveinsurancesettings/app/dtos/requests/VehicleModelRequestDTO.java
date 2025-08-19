package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record VehicleModelRequestDTO(
        String name,

        String motorizationType,

        String bodywork,

        Integer placeCount,

        Boolean hasTurbo,

        BigDecimal horsepower,

        BigDecimal displacement, // cylindrée en litre

        BigDecimal weight, // poids en kilogramme

        String nature
    ) {

}

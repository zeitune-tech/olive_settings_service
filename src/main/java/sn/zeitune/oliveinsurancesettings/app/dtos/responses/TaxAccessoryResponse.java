package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record TaxAccessoryResponse(
        UUID id,
        String name,
        LocalDate dateEffective,
        CalculationBase calculationBase,
        Boolean isFlatRate,
        Double flatRateAmount,
        Double rate,
        TaxTypeResponse taxType,
        ProductResponse product
) {
}

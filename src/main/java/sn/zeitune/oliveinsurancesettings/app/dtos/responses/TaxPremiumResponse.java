package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record TaxPremiumResponse(
        UUID id,
        String name,
        LocalDate dateEffective,
        CalculationBase calculationBase,
        Double rate,
        Boolean isFlatRate,
        Double flatRateAmount,
        TaxTypeResponse taxType,
        ProductResponse product,
        CoverageResponse coverage

) {
}

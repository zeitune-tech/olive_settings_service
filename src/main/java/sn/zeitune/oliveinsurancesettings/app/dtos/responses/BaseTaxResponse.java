package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record BaseTaxResponse(
        UUID id,
        LocalDate dateEffective,
        CalculationBase calculationBase,
        boolean isFlat,
        Double rate,
        Double fixedAmount,
        TaxResponse tax,
        ProductResponseDTO product,
        CoverageReferenceResponse coverage,
        UUID managementEntityId
) {}

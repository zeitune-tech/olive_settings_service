package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CommissionContributorResponse(
        UUID id,
        LocalDate dateEffective,
        CalculationBase commissionBase,
        Double contributorRate,
        Double upperEntityContributorRate,
        UUID contributorId,
        ProductResponseDTO product
) {}

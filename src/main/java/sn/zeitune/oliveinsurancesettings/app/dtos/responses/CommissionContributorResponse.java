package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CommissionContributorResponse(
        UUID id,
        LocalDate dateEffective,
        CalculationBase calculationBase,
        Double managementRate,
        Double contributionRate,
        UUID contributorId,
        ProductResponse product,
        CoverageResponse coverage,
        ContributorResponse contributor,
        ContributorTypeResponse contributorType
) {}

package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

@Builder
public record CommissionPointOfSaleResponse(
        UUID id,
        LocalDate dateEffective,
        CalculationBase calculationBase,
        Double managementRate,
        Double contributionRate,
        ManagementEntityResponse pointOfSale,
        CoverageResponse coverage,
        ProductResponse product
) {}

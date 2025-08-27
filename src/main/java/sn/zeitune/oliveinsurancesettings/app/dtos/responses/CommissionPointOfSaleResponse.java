package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;
import sn.zeitune.oliveinsurancesettings.enums.PointOfSaleType;

@Builder
public record CommissionPointOfSaleResponse(
        UUID id,
        LocalDate dateEffective,
        CalculationBase calculationBase,
        Double managementRate,
        Double contributionRate,
        PointOfSaleType pointOfSaleType,
        ManagementEntityResponse pointOfSale,
        CoverageResponse coverage,
        ProductResponse product
) {}

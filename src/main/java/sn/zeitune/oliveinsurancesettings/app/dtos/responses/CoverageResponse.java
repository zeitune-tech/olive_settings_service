package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;
import sn.zeitune.oliveinsurancesettings.enums.CoverageNature;

import java.util.UUID;

@Builder
public record CoverageResponse(

        UUID id,
        CoverageNature nature,
        boolean isFree,
        boolean isFlatRate,
        CalculationMode calculationMode,
        boolean isFixed,
        Long fixedCapital,
        Long minCapital,
        Long maxCapital,
        int order,
        boolean prorata,
        boolean displayPrime,
        boolean generatesCharacteristic,
        CoverageReferenceResponse reference,
        ProductResponse product,
        ManagementEntityResponse managementEntity

) {}

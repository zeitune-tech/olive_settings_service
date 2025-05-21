package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;

import java.util.UUID;

@Builder
public record CoverageResponse(

        UUID id,
        String nature,
        boolean isFree,
        boolean isFixed,
        CalculationMode calculationMode,
        Long fixedCapital,
        Long minCapital,
        Long maxCapital,
        int order,
        String prorata,
        boolean displayPrime,
        boolean generatesCharacteristic,
        CoverageReferenceResponse reference,
        UUID product,
        UUID managementEntity

) {}

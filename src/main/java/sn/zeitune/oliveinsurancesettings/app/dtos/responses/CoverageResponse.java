package sn.zeitune.oliveinsurancesettings.app.dto.response;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CoverageResponse(

        Long id,
        UUID uuid,
        String nature,
        boolean isFree,
        boolean isFixed,
        CalculationMode calculationMode,
        BigDecimal fixedCapital,
        BigDecimal minCapital,
        BigDecimal maxCapital,
        int order,
        String prorata,
        boolean displayPrime,
        boolean generatesCharacteristic,
        UUID coverageReferenceId,
        UUID product,
        UUID managementEntity

) {}

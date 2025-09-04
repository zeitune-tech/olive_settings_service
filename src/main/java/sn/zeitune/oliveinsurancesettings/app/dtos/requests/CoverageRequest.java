package sn.zeitune.oliveinsurancesettings.app.dtos.requests;


import jakarta.validation.constraints.*;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;
import sn.zeitune.oliveinsurancesettings.enums.CoverageNature;


@Builder
public record CoverageRequest(

        CoverageNature nature,

        @NotNull(message = "Free status must be specified")
        Boolean isFree,

        boolean isFlatRate,

        @NotNull(message = "Calculation mode is required")
        CalculationMode calculationMode,

        @DecimalMin(value = "0.0", inclusive = true, message = "Fixed capital must be positive")
        Long fixedCapital,

        @DecimalMin(value = "0.0", inclusive = true, message = "Minimum capital must be non-negative")
        Long minCapital,

        @DecimalMin(value = "0.0", inclusive = true, message = "Maximum capital must be non-negative")
        Long maxCapital,

        @Min(value = 0, message = "Order must be non-negative")
        int order,

        boolean prorata,

        boolean isFixed,

        String clause,

        @NotNull(message = "Display prime must be specified")
        Boolean displayPrime,

        @NotNull(message = "Characteristic generation flag must be specified")
        Boolean generatesCharacteristic

) {
}


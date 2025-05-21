package sn.zeitune.oliveinsurancesettings.app.dtos.requests;


import jakarta.validation.constraints.*;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;


@Builder
public record CoverageRequest(

        @NotBlank(message = "Nature is required")
        String nature,

        @NotNull(message = "Free status must be specified")
        Boolean isFree,

        @NotNull(message = "Fixed status must be specified")
        Boolean isFixed,

        @NotNull(message = "Calculation mode is required")
        CalculationMode calculationMode,

        @DecimalMin(value = "0.0", inclusive = false, message = "Fixed capital must be positive")
        Long fixedCapital,

        @DecimalMin(value = "0.0", inclusive = true, message = "Minimum capital must be non-negative")
        Long minCapital,

        @DecimalMin(value = "0.0", inclusive = true, message = "Maximum capital must be non-negative")
        Long maxCapital,

        @Min(value = 0, message = "Order must be non-negative")
        int order,

        @NotBlank(message = "Prorata mode is required")
        String prorata,

        @NotNull(message = "Display prime must be specified")
        Boolean displayPrime,

        @NotNull(message = "Characteristic generation flag must be specified")
        Boolean generatesCharacteristic

) {}


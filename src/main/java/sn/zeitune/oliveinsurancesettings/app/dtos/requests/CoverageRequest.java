package sn.zeitune.oliveinsurancesettings.app.dto.request;


import jakarta.validation.constraints.*;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;

import java.math.BigDecimal;
import java.util.UUID;

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
        BigDecimal fixedCapital,

        @DecimalMin(value = "0.0", inclusive = true, message = "Minimum capital must be non-negative")
        BigDecimal minCapital,

        @DecimalMin(value = "0.0", inclusive = true, message = "Maximum capital must be non-negative")
        BigDecimal maxCapital,

        @Min(value = 0, message = "Order must be non-negative")
        int order,

        @NotBlank(message = "Prorata mode is required")
        String prorata,

        @NotNull(message = "Display prime must be specified")
        Boolean displayPrime,

        @NotNull(message = "Characteristic generation flag must be specified")
        Boolean generatesCharacteristic,

        @NotNull(message = "Coverage reference ID is required")
        UUID coverageReferenceId,

        @NotNull(message = "Product ID is required")
        UUID product,

        @NotNull(message = "Management entity ID is required")
        UUID managementEntity

) {}


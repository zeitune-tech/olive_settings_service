package sn.zeitune.oliveinsurancesettings.app.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CoverageDurationType;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CoverageDurationRequest(
        @NotNull(message = "Start date is required")
        @PastOrPresent(message = "Start date must be in the past or present")
        LocalDate from,

        @NotNull(message = "End date is required")
        @FutureOrPresent(message = "End date must be today or in the future")
        LocalDate to,

        @NotNull(message = "Coverage duration type is required")
        CoverageDurationType type,

        @NotBlank(message = "Prorata mode is required")
        String prorotaMode,

        @NotBlank(message = "Unit is required")
        String unit,

        @NotNull(message = "Management entity ID is required")
        UUID managementEntity
) {
}

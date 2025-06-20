package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CoverageDurationType;
import sn.zeitune.oliveinsurancesettings.enums.Unit;

@Builder
public record CoverageDurationRequest(
        @NotNull(message = "Start date is required")
        Double from,

        @NotNull(message = "End date is required")
        Double to,

        @NotNull(message = "Coverage duration type is required")
        CoverageDurationType type,

        @NotBlank(message = "Prorata mode is required")
        String designation,

        @NotNull(message = "Unit is required")
        Unit unit
) {
}

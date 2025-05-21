package sn.zeitune.oliveinsurancesettings.app.dto.request;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CoverageReferenceRequest(
        @NotBlank(message = "Designation must not be blank")
        String designation,

        @NotBlank(message = "Family must not be blank")
        String family,

        @NotNull(message = "Access to characteristics must be specified")
        Boolean accessCharacteristic,

        @NotNull(message = "Tariff access must be specified")
        Boolean tariffAccess,

        @NotNull(message = "Management entity ID is required")
        UUID managementEntity
) {
}

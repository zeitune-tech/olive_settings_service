package sn.zeitune.oliveinsurancesettings.app.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductionRegistryRequest(

        @NotBlank(message = "Prefix must not be blank")
        String prefix,

        @Min(value = 1, message = "Length must be at least 1")
        int length,

        @NotNull(message = "Management entity ID is required")
        UUID managementEntity

) {}
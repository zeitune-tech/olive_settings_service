package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record ProductRequestDTO(
        @NotNull(message = "Branch UUID is required")
        UUID branch,

        @NotNull(message = "Product name is required")
        @NotBlank(message = "Product name must not be blank")
        String name,

        @NotNull(message = "Product description is required")
        @NotBlank(message = "Product description must not be blank")
        String description,

        @Min(value = 1, message = "Minimum risk must be at least 1")
        Integer minRisk,

        @Min(value = 1, message = "Maximum risk must be at least 1")
        Integer maxRisk,

        @Min(value = 1, message = "Minimum guarantee number must be at least 1")
        Integer minimumGuaranteeNumber,

        @NotNull(message = "Fleet must be specified")
        Boolean fleet,

        boolean hasReduction,
        boolean canBeRepartitioned,

        @NotNull(message = "Coverages must not be null")
        Set<UUID> coverages
) {}

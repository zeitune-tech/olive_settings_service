package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record VehicleCategoryUpdateRequestDTO(
        @NotNull
        @NotEmpty
        String name,
        @NotNull
        @NotEmpty
        Boolean withTrailer,
        @NotNull
        @NotEmpty
        Boolean withChassis,
        Set<UUID> usages,
        Set<UUID> products
) {
    public VehicleCategoryUpdateRequestDTO {
        // Ensure sets are not null to avoid NullPointerExceptions
        if (usages == null) usages = Set.of();
        if (products == null) products = Set.of();
    }
}

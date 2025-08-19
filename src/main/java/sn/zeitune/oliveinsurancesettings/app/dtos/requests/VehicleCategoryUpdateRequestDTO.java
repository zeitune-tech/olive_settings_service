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

}

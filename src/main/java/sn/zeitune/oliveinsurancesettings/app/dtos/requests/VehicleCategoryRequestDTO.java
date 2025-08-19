package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VehicleCategoryRequestDTO(
        @NotNull
        @NotEmpty
        String name,
        @NotNull
        @NotEmpty
        Boolean withTrailer,
        @NotNull
        @NotEmpty
        Boolean withChassis
    ) {

}

package sn.zeitune.oliveinsurancesettings.app.dtos.requests;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record IncompatibleCoverageRequest(

        @NotNull(message = "Coverage ID is required")
        UUID coverageId,

        @NotNull(message = "Incompatible coverage ID is required")
        UUID incompatibleId
) {}


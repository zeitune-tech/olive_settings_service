package sn.zeitune.oliveinsurancesettings.app.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record IncompatibleCoverageRequest(

        @NotNull(message = "Coverage UUID is required")
        UUID coverageUuid,

        @NotNull(message = "Incompatible coverage UUID is required")
        UUID incompatibleCoverageUuid,

        @NotNull(message = "Management entity ID is required")
        UUID managementEntity

) {}


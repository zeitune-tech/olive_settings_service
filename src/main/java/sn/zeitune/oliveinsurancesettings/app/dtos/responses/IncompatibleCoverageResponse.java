package sn.zeitune.oliveinsurancesettings.app.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record IncompatibleCoverageResponse(

        Long id,
        UUID uuid,
        UUID coverageUuid,
        UUID incompatibleCoverageUuid,
        UUID managementEntity

) {}
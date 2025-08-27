package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record IncompatibleCoverageResponse(
        UUID id,
        CoverageResponse coverage,
        CoverageResponse incompatibleCoverage,
        UUID managementEntity
) {}
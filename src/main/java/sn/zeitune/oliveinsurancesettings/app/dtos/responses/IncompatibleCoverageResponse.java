package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record IncompatibleCoverageResponse(
        UUID id,
        CoverageReferenceResponse coverage,
        CoverageReferenceResponse incompatibleCoverage,
        UUID managementEntity
) {}
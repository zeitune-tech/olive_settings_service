package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.IncompatibleCoverageRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.IncompatibleCoverageResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.IncompatibleCoverage;

public class IncompatibleCoverageMapper {

    public static IncompatibleCoverage map(
            IncompatibleCoverageRequest request,
            Coverage coverage,
            Coverage incompatibleCoverage
    ) {
        return IncompatibleCoverage.builder()
                .coverage(coverage)
                .incompatibleCoverage(incompatibleCoverage)
                .build();
    }

    public static IncompatibleCoverageResponse map(IncompatibleCoverage entity) {
        return IncompatibleCoverageResponse.builder()
                .id(entity.getUuid())
                .coverage(CoverageReferenceMapper.map(entity.getCoverage().getCoverageReference()))
                .incompatibleCoverage(CoverageReferenceMapper.map(entity.getIncompatibleCoverage().getCoverageReference()))
                .managementEntity(entity.getManagementEntity())
                .build();
    }
}

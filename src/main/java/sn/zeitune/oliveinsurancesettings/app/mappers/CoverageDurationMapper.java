package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageDurationRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageDurationResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageDuration;

public class CoverageDurationMapper {

    public static CoverageDuration map(CoverageDurationRequest request) {
        return CoverageDuration.builder()
                .from(request.from())
                .to(request.to())
                .type(request.type())
                .designation(request.designation())
                .unit(request.unit())
                .build();
    }

    public static CoverageDurationResponse map(CoverageDuration duration) {
        if (duration == null) return null;

        return CoverageDurationResponse.builder()
                .id(duration.getUuid())
                .from(duration.getFrom())
                .to(duration.getTo())
                .type(duration.getType())
                .designation(duration.getDesignation())
                .unit(duration.getUnit())
                .managementEntity(duration.getManagementEntity())
                .build();
    }
}


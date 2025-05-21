package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ClosureRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ClosureResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Closure;

public class ClosureMapper {

    public static Closure map(ClosureRequest request) {
        return Closure.builder()
                .type(request.type())
                .date(request.date())
                .build();
    }

    public static ClosureResponse map(Closure closure) {
        return ClosureResponse.builder()
                .id(closure.getUuid())
                .type(closure.getType())
                .date(closure.getDate())
                .managementEntity(closure.getManagementEntity())
                .build();
    }
}


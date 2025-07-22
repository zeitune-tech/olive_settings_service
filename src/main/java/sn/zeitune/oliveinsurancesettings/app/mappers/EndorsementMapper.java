package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.EndorsementRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.EndorsementResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.Endorsement;

public class EndorsementMapper {

    public static Endorsement map(EndorsementRequest request) {
        return Endorsement.builder()
                .name(request.name())
                .nature(request.nature())
                .build();
    }

    public static EndorsementResponse map(Endorsement endorsement) {
        return new EndorsementResponse(
                endorsement.getUuid(),
                endorsement.getName(),
                endorsement.getNature()
        );
    }
}

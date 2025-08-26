package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ActivityResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Activity;

public class ActivityMapper {
    public static ActivityResponse map(Activity entity) {
        return new ActivityResponse(
                entity.getUuid(),
                entity.getCode(),
                entity.getLibelle(),
                entity.isActif(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}


package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProfessionResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Profession;

public class ProfessionMapper {
    public static ProfessionResponse map(Profession entity) {
        return new ProfessionResponse(
                entity.getUuid(),
                entity.getCode(),
                entity.getLibelle(),
                entity.isActif(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}


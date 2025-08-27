package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.QualityResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Quality;

public class QualityMapper {
    public static QualityResponse map(Quality entity) {
        return new QualityResponse(
                entity.getUuid(),
                entity.getCode(),
                entity.getLibelle(),
                entity.isActif(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}


package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.GenreResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Genre;

public class GenreMapper {
    public static GenreResponse map(Genre entity) {
        return new GenreResponse(
                entity.getUuid(),
                entity.getCode(),
                entity.getLibelle(),
                entity.isActif(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}


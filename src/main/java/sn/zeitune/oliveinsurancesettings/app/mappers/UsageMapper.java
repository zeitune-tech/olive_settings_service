package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.GenreResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.UsageResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Usage;

public class UsageMapper {
    public static UsageResponse map(Usage entity) {
        GenreResponse genre = entity.getGenre() != null ? GenreMapper.map(entity.getGenre()) : null;
        return new UsageResponse(
                entity.getUuid(),
                entity.getCode(),
                entity.getLibelle(),
                entity.isActif(),
                genre,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

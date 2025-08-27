package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.UsageResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Usage;

public class UsageMapper {
    public static UsageResponse map(Usage entity) {
        return new UsageResponse(
                entity.getUuid(),
                entity.getCode(),
                entity.getLibelle(),
                entity.isActif(),
                entity.getGenre() != null ? entity.getGenre().getUuid() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}


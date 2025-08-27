package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CityResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.City;

public class CityMapper {
    public static CityResponse map(City entity) {
        return new CityResponse(
                entity.getUuid(),
                entity.getCode(),
                entity.getLibelle(),
                entity.isActif(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}


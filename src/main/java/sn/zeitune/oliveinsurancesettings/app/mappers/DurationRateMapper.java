package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.DurationRateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.DurationRateResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.DurationRate;

public class DurationRateMapper {

    public static DurationRate map(DurationRateRequest request) {
        return DurationRate.builder()
                .dateEffective(request.dateEffective())
                .rate(request.rate())
                .build();
    }


    public static DurationRateResponse map(DurationRate entity, ProductResponseDTO product) {
        return DurationRateResponse.builder()
                .id(entity.getUuid())
                .dateEffective(entity.getDateEffective())
                .duration(CoverageDurationMapper.map(entity.getDuration()))
                .rate(entity.getRate())
                .product(product)
                .build();
    }
}

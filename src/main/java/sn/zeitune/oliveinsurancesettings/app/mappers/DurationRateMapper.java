package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.DurationRateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.DurationRateResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageDurationRate;

public class DurationRateMapper {

    public static CoverageDurationRate map(DurationRateRequest request) {
        return CoverageDurationRate.builder()
                .dateEffective(request.dateEffective())
                .rate(request.rate())
                .build();
    }


    public static DurationRateResponse map(CoverageDurationRate entity, ProductResponseDTO product) {
        return DurationRateResponse.builder()
                .id(entity.getUuid())
                .dateEffective(entity.getDateEffective())
                .duration(CoverageDurationMapper.map(entity.getDuration()))
                .rate(entity.getRate())
                .product(product)
                .build();
    }
}

package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.DurationRateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.DurationRateResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.DurationRate;

public class DurationRateMapper {

    public static DurationRate map(DurationRateRequest request) {
        return DurationRate.builder()
                .dateEffective(request.dateEffective())
                .rate(request.rate())
                .product(null)  // to be set by service
                .build();
    }

    public static DurationRateResponse map(DurationRate entity) {
        return DurationRateResponse.builder()
                .uuid(entity.getUuid())
                .dateEffective(entity.getDateEffective())
                .rate(entity.getRate())
                .productId(entity.getProduct())
                .build();
    }
}

package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Commission;

public class CommissionMapper {

    public static Commission map(CommissionRequest request) {
        return Commission.builder()
                .dateEffective(request.dateEffective())
                .calculationBase(request.calculationBase())
                .managementRate(request.managementRate())
                .contributionRate(request.contributionRate())
                .pointOfSale(null) // to be set by service if needed
                .product(null)    // to be set by service if needed
                .build();
    }

    public static CommissionResponse map(Commission commission, ProductResponseDTO product) {
        return CommissionResponse.builder()
                .id(commission.getUuid())
                .dateEffective(commission.getDateEffective())
                .calculationBase(commission.getCalculationBase())
                .managementRate(commission.getManagementRate())
                .contributionRate(commission.getContributionRate())
                .salesPointId(commission.getPointOfSale())
                .product(product)
                .build();
    }
}

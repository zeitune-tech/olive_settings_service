package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionContributorResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.CommissionContributor;

public class CommissionContributorMapper {

    public static CommissionContributor map(CommissionContributorRequest request) {
        return CommissionContributor.builder()
                .dateEffective(request.dateEffective())
                .commissionBase(request.commissionBase())
                .contributorRate(request.contributorRate())
                .upperEntityContributorRate(request.upperEntityContributorRate())
                .contributor(null) // to be set by service
                .product(null)     // to be set by service
                .build();
    }

    public static CommissionContributorResponse map(CommissionContributor entity, ProductResponseDTO product) {
        return CommissionContributorResponse.builder()
                .id(entity.getUuid())
                .dateEffective(entity.getDateEffective())
                .commissionBase(entity.getCommissionBase())
                .contributorRate(entity.getContributorRate())
                .upperEntityContributorRate(entity.getUpperEntityContributorRate())
                .contributorId(entity.getContributor() )
                .product(product)
                .build();
    }
}

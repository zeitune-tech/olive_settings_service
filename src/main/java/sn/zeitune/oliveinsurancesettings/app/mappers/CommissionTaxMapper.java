package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionTaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionTaxResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.CommissionTax;

public class CommissionTaxMapper {

    public static CommissionTax map(CommissionTaxRequest request) {
        return CommissionTax.builder()
                .dateEffective(request.dateEffective())
                .commissionTaxType(request.commissionTaxType())
                .rate(request.rate())
                .pointOfSale(request.pointOfSaleId())
                .product(request.productId())
                .build();
    }

    public static CommissionTaxResponse map(CommissionTax commissionTax, ProductResponseDTO product) {
        return CommissionTaxResponse.builder()
                .id(commissionTax.getUuid())
                .dateEffective(commissionTax.getDateEffective())
                .commissionTaxType(commissionTax.getCommissionTaxType())
                .rate(commissionTax.getRate())
                .pointOfSale(commissionTax.getPointOfSale())
                .product(product)
                .managementEntityId(commissionTax.getManagementEntity())
                .build();
    }
}

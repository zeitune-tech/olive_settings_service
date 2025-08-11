package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxCommissionsContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxCommissionsPointOfSaleRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxCommissionsContributorResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxCommissionsPointOfSaleResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.TaxCommissionsContributor;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.TaxCommissionsPointOfSale;

public class TaxCommissionMapper {

    // =============================
    // Contributor
    // =============================

    public static TaxCommissionsContributor toContributorEntity(
            TaxCommissionsContributorRequest request
    ) {
        return TaxCommissionsContributor.builder()
                .dateEffective(request.dateEffective())
                .rate(request.rate())
                .toWithhold(request.toWithhold())
                .managementEntity(request.managementEntity())
                .contributor(request.contributorId())
                .contributorType(request.contributorTypeId())
                .build();
    }

    public static TaxCommissionsContributorResponse toContributorResponse(
            TaxCommissionsContributor entity
    ) {
        return new TaxCommissionsContributorResponse(
                entity.getUuid(),
                entity.getDateEffective(),
                entity.getRate(),
                entity.getToWithhold(),
                entity.getManagementEntity(),
                entity.getContributor(),
                entity.getContributorType()
        );
    }

    // =============================
    // Point of Sale
    // =============================

    public static TaxCommissionsPointOfSale toPointOfSaleEntity(
            TaxCommissionsPointOfSaleRequest request
    ) {
        return TaxCommissionsPointOfSale.builder()
                .dateEffective(request.dateEffective())
                .rate(request.rate())
                .toWithhold(request.toWithhold())
                .managementEntity(request.managementEntity())
                .pointOfSale(request.pointOfSaleId())
                .pointOfSaleType(request.pointOfSaleType())
                .build();
    }

    public static TaxCommissionsPointOfSaleResponse toPointOfSaleResponse(
            TaxCommissionsPointOfSale entity
    ) {
        return new TaxCommissionsPointOfSaleResponse(
                entity.getUuid(),
                entity.getDateEffective(),
                entity.getRate(),
                entity.getToWithhold(),
                entity.getManagementEntity(),
                entity.getPointOfSale(),
                entity.getPointOfSaleType()
        );
    }
}

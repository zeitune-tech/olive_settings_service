package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionPointOfSaleRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxCommissionsContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.*;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.*;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

public  class CommissionMapper {

    public static CommissionContributorAccessory map(
            CommissionContributorRequest request,
            Product product
    ) {

        return CommissionContributorAccessory.builder()
                .dateEffective(request.dateEffective())
                .managementRate(request.managementRate())
                .contributionRate(request.contributionRate())
                .contributorType(request.contributorTypeId())
                .contributor(request.contributorId())
                .product(product)
                .build();
    }

    public static CommissionContributorPremium map(
            CommissionContributorRequest request,
            Coverage coverage,
            Product product
    ) {

        return CommissionContributorPremium.builder()
                .dateEffective(request.dateEffective())
                .contributionRate(request.contributionRate())
                .managementRate(request.managementRate())
                .contributorType(request.contributorTypeId())
                .contributor(request.contributorId())
                .coverage(coverage)
                .product(product)
                .build();
    }

    public static CommissionPointOfSaleAccessory map(
            CommissionPointOfSaleRequest request,
            Product product
    ) {

        return CommissionPointOfSaleAccessory.builder()
                .dateEffective(request.dateEffective())
                .managementRate(request.managementRate())
                .contributionRate(request.contributionRate())
                .pointOfSaleType(request.pointOfSaleType())
                .pointOfSale(request.salesPointId())
                .product(product)
                .build();
    }

    public static CommissionPointOfSalePremium map(
            CommissionPointOfSaleRequest request,
            Coverage coverage,
            Product product
    ) {
        return CommissionPointOfSalePremium.builder()
                .dateEffective(request.dateEffective())
                .managementRate(request.managementRate())
                .contributionRate(request.contributionRate())
                .pointOfSaleType(request.pointOfSaleType())
                .pointOfSale(request.salesPointId())
                .coverage(coverage)
                .product(product)
                .build();
    }

    public static TaxCommissionsContributor map(TaxCommissionsContributorRequest request) {
        return TaxCommissionsContributor.builder()
                .dateEffective(request.dateEffective())
                .rate(request.rate())
                .toWithhold(request.toWithhold())
                .contributorType(request.contributorTypeId())
                .contributor(request.contributorId())
                .build();
    }

    public static CommissionPointOfSaleResponse map(
            CommissionPointOfSale commissionPointOfSale,
            ProductResponse product,
            CoverageResponse coverage,
            ManagementEntityResponse pointOfSale
    ) {

        CalculationBase calculationBase;

        if (commissionPointOfSale instanceof CommissionPointOfSaleAccessory) {
            calculationBase = CalculationBase.ACCESSORY;
        } else if (commissionPointOfSale instanceof CommissionPointOfSalePremium) {
            calculationBase = CalculationBase.PRIME;
        } else {
            throw new IllegalArgumentException("Unknown commission type: " + commissionPointOfSale.getClass().getSimpleName());
        }

        return CommissionPointOfSaleResponse.builder()
                .id(commissionPointOfSale.getUuid())
                .dateEffective(commissionPointOfSale.getDateEffective())
                .calculationBase(calculationBase)
                .managementRate(commissionPointOfSale.getManagementRate())
                .contributionRate(commissionPointOfSale.getContributionRate())
                .product(product)
                .coverage(coverage)
                .pointOfSale(pointOfSale)
                .build();
    }

    public static CommissionContributorResponse map(
            CommissionContributor commissionContributor,
            ProductResponse product,
            CoverageResponse coverage,
            ContributorResponse contributor
    ) {

        CalculationBase calculationBase;

        if (commissionContributor instanceof CommissionContributorAccessory) {
            calculationBase = CalculationBase.ACCESSORY;
        } else if (commissionContributor instanceof CommissionContributorPremium) {
            calculationBase = CalculationBase.PRIME;
        } else {
            throw new IllegalArgumentException("Unknown commission type: " + commissionContributor.getClass().getSimpleName());
        }

        return CommissionContributorResponse.builder()
                .id(commissionContributor.getUuid())
                .dateEffective(commissionContributor.getDateEffective())
                .calculationBase(calculationBase)
                .contributor(contributor)
                .managementRate(commissionContributor.getManagementRate())
                .contributionRate(commissionContributor.getContributionRate())
                .product(product)
                .coverage(coverage)
                .build();
    }


}

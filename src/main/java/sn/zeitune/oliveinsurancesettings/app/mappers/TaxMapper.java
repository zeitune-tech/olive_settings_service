package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxExemptionResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxExemptionRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxTypeRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.*;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.*;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.stamp.Stamp;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.util.Set;

public class TaxMapper {

    public static TaxPremium map(
            TaxRequest request,
            TaxType type,
            Coverage coverage,
            Product product
    ) {
        return TaxPremium.builder()
                .dateEffective(request.dateEffective())
                .rate(request.rate())
                .isFlatRate(request.isFlatRate())
                .flatRateAmount(request.flatRateAmount())
                .name(request.name())
                .taxType(type)
                .coverage(coverage)
                .product(product)
                .build();
    }

    public static TaxAccessory map(
            TaxRequest request,
            TaxType type,
            Product product
    ) {
        return TaxAccessory.builder()
                .dateEffective(request.dateEffective())
                .rate(request.rate())
                .isFlatRate(request.isFlatRate())
                .flatRateAmount(request.flatRateAmount())
                .name(request.name())
                .taxType(type)
                .product(product)
                .build();
    }

    public static TaxType map(
            TaxTypeRequest request
    ) {
        return TaxType.builder()
                .name(request.name())
                .nature(request.nature())
                .build();
    }

    public static TaxExemption map(
            TaxExemptionRequest request,
            Product product,
            Set<Tax> taxes
    ) {
        return TaxExemption.builder()
                .name(request.name())
                .product(product)
                .taxes(taxes)
                .build();
    }


    public static TaxPremiumResponse map(
            TaxPremium tax,
            TaxTypeResponse taxType,
            CoverageResponse coverage,
            ProductResponse product
    ) {
        return TaxPremiumResponse.builder()
                .id(tax.getUuid())
                .name(tax.getName())
                .calculationBase(CalculationBase.PRIME)
                .dateEffective(tax.getDateEffective())
                .isFlatRate(tax.getIsFlatRate())
                .flatRateAmount(tax.getFlatRateAmount())
                .rate(tax.getRate())
                .taxType(taxType)
                .coverage(coverage)
                .product(product)
                .build();
    }

    public static TaxAccessoryResponse map(
            TaxAccessory tax,
            TaxTypeResponse taxType,
            ProductResponse product
    ) {
        return TaxAccessoryResponse.builder()
                .id(tax.getUuid())
                .name(tax.getName())
                .calculationBase(CalculationBase.ACCESSORY)
                .dateEffective(tax.getDateEffective())
                .isFlatRate(tax.getIsFlatRate())
                .flatRateAmount(tax.getFlatRateAmount())
                .taxType(taxType)
                .rate(tax.getRate())
                .product(product)
                .build();
    }

    public static TaxTypeResponse map(
            TaxType taxType
    ) {
        return TaxTypeResponse.builder()
                .id(taxType.getUuid())
                .name(taxType.getName())
                .nature(taxType.getNature())
                .build();
    }

    public static TaxExemptionResponse map(
            TaxExemption taxExemption,
            ProductResponse product,
            Set<TaxResponse> taxes
    ) {
        return TaxExemptionResponse.builder()
                .id(taxExemption.getUuid())
                .name(taxExemption.getName())
                .product(product)
                .taxes(taxes)
                .build();
    }

    public static TaxResponse map(
            Tax tax
    ) {

        String taxType = switch (tax) {
            case TaxPremium taxPremium -> "PRIME";
            case TaxAccessory taxAccessory -> "ACCESSORY";
            case Stamp stamp -> "STAMP";
            case null, default -> "UNKNOWN";
        };


        return TaxResponse.builder()
                .id(tax.getUuid())
                .name(tax.getName())
                .rate(tax.getRate())
                .type(taxType)
                .build();
    }

}

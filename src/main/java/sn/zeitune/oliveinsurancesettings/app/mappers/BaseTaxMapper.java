package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.BaseTaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.BaseTaxResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageReferenceResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseTax;

import java.util.UUID;

public class BaseTaxMapper {

    public static BaseTax map(BaseTaxRequest request) {
        return BaseTax.builder()
                .dateEffective(request.dateEffective())
                .calculationBase(request.calculationBase())
                .isFlat(request.isFlat())
                .rate(request.rate())
                .fixedAmount(request.fixedAmount())
                .product(null)     // to be set by service
                .tax(null)         // to be set by service
                .build();
    }

    public static BaseTaxResponse map(BaseTax baseTax, TaxResponse tax, CoverageReferenceResponse coverage, ProductResponseDTO product) {
        return BaseTaxResponse.builder()
                .id(baseTax.getUuid())
                .dateEffective(baseTax.getDateEffective())
                .calculationBase(baseTax.getCalculationBase())
                .isFlat(baseTax.isFlat())
                .rate(baseTax.getRate())
                .fixedAmount(baseTax.getFixedAmount())
                .tax(tax)
                .coverage(coverage)
                .product(product)
                .build();
    }
}

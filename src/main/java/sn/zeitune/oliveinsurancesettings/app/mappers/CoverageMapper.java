package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageReference;

public class CoverageMapper {

    public static Coverage map(CoverageRequest request, CoverageReference reference) {
        return Coverage.builder()
                .nature(request.nature())
                .isFree(request.isFree())
                .isFlatRate(request.isFlatRate())
                .calculationMode(request.calculationMode())
                .fixedCapital(request.fixedCapital())
                .minCapital(request.minCapital())
                .maxCapital(request.maxCapital())
                .order(request.order())
                .prorata(request.prorata())
                .clause(request.clause())
                .displayPrime(request.displayPrime())
                .generatesCharacteristic(request.generatesCharacteristic())
                .coverageReference(reference)
                .build();
    }

    public static CoverageResponse map(Coverage coverage, ProductResponse product) {
        return CoverageResponse.builder()
                .id(coverage.getUuid())
                .nature(coverage.getNature())
                .isFree(coverage.isFree())
                .isFlatRate(coverage.isFlatRate())
                .calculationMode(coverage.getCalculationMode())
                .fixedCapital(coverage.getFixedCapital())
                .minCapital(coverage.getMinCapital())
                .maxCapital(coverage.getMaxCapital())
                .order(coverage.getOrder())
                .prorata(coverage.isProrata())
                .displayPrime(coverage.isDisplayPrime())
                .generatesCharacteristic(coverage.isGeneratesCharacteristic())
                .reference(CoverageReferenceMapper.map(coverage.getCoverageReference()))
                .product(product)
                .managementEntity(ManagementEntityResponse.builder().id(coverage.getManagementEntity()).build())
                .build();
    }

    public static CoverageResponse map(Coverage coverage, ProductResponse product, ManagementEntityResponse managementEntityResponse) {

        if (coverage == null) {
            return null;
        }

        return CoverageResponse.builder()
                .id(coverage.getUuid())
                .nature(coverage.getNature())
                .isFree(coverage.isFree())
                .isFlatRate(coverage.isFlatRate())
                .calculationMode(coverage.getCalculationMode())
                .fixedCapital(coverage.getFixedCapital())
                .minCapital(coverage.getMinCapital())
                .maxCapital(coverage.getMaxCapital())
                .order(coverage.getOrder())
                .prorata(coverage.isProrata())
                .displayPrime(coverage.isDisplayPrime())
                .generatesCharacteristic(coverage.isGeneratesCharacteristic())
                .reference(CoverageReferenceMapper.map(coverage.getCoverageReference()))
                .product(product)
                .managementEntity(managementEntityResponse)
                .build();
    }
}


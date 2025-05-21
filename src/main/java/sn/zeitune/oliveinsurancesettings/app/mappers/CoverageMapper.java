package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.CoverageReference;

public class CoverageMapper {

    public static Coverage map(CoverageRequest request, CoverageReference reference) {
        return Coverage.builder()
                .nature(request.nature())
                .isFree(request.isFree())
                .isFixed(request.isFixed())
                .calculationMode(request.calculationMode())
                .fixedCapital(request.fixedCapital())
                .minCapital(request.minCapital())
                .maxCapital(request.maxCapital())
                .order(request.order())
                .prorata(request.prorata())
                .displayPrime(request.displayPrime())
                .generatesCharacteristic(request.generatesCharacteristic())
                .coverageReference(reference)
                .build();
    }

    public static CoverageResponse map(Coverage coverage) {
        return CoverageResponse.builder()
                .id(coverage.getUuid())
                .nature(coverage.getNature())
                .isFree(coverage.isFree())
                .isFixed(coverage.isFixed())
                .calculationMode(coverage.getCalculationMode())
                .fixedCapital(coverage.getFixedCapital())
                .minCapital(coverage.getMinCapital())
                .maxCapital(coverage.getMaxCapital())
                .order(coverage.getOrder())
                .prorata(coverage.getProrata())
                .displayPrime(coverage.isDisplayPrime())
                .generatesCharacteristic(coverage.isGeneratesCharacteristic())
                .reference(CoverageReferenceMapper.map(coverage.getCoverageReference()))
                .product(coverage.getProduct())
                .managementEntity(coverage.getManagementEntity())
                .build();
    }
}


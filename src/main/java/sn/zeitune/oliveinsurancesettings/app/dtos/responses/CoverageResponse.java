package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;
import sn.zeitune.oliveinsurancesettings.enums.CoverageNature;

import java.util.UUID;

@Builder
public record CoverageResponse(

        UUID id,
        CoverageNature nature,
        boolean isFree,
        boolean isFlatRate,
        CalculationMode calculationMode,
        Long fixedCapital,
        Long minCapital,
        Long maxCapital,
        int order,
        boolean prorata,
        boolean displayPrime,
        boolean generatesCharacteristic,
        CoverageReferenceResponse reference,
        ProductResponseDTO product,
        ManagementEntityResponse managementEntity

) {}

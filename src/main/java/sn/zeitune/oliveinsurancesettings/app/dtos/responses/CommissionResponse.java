package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

@Builder
public record CommissionResponse(
        UUID id,
        LocalDate dateEffective,
        CalculationBase calculationBase,
        Double managementRate,
        Double contributionRate,
        UUID salesPointId,
        ProductResponseDTO product
) {}

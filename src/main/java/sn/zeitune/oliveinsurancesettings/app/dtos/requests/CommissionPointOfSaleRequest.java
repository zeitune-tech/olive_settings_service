package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;
import sn.zeitune.oliveinsurancesettings.enums.PointOfSaleType;

@Builder
public record CommissionPointOfSaleRequest(

        @NotNull(message = "Effective date must not be null")
        @PastOrPresent(message = "Effective date cannot be in the future")
        LocalDate dateEffective,

        @NotNull(message = "Calculation base must not be null")
        CalculationBase calculationBase,

        @NotNull(message = "Management rate must not be null")
        Double managementRate,

        @NotNull(message = "Contribution rate must not be null")
        Double contributionRate,

        UUID pointOfSaleId,

        PointOfSaleType pointOfSaleType,

        @NotNull(message = "Product ID must not be null")
        UUID productId,

        UUID coverageId
) {}

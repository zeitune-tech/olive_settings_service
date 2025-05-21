package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record BaseTaxRequest(

        @NotNull(message = "Effective date must not be null")
        @PastOrPresent(message = "Effective date cannot be in the future")
        LocalDate dateEffective,

        @NotNull(message = "Calculation base must not be null")
        CalculationBase calculationBase,

        @NotNull(message = "Is flat must not be specified")
        boolean isFlat,

        Double rate,

        Double fixedAmount,

        @NotNull(message = "Tax ID must not be null")
        UUID taxId,

        @NotNull(message = "Product ID must not be null")
        UUID productId,

        UUID coverageId
) {}

package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CommissionTaxType;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CommissionTaxRequest(

        @NotNull(message = "Effective date must not be null")
        @PastOrPresent(message = "Effective date cannot be in the future")
        LocalDate dateEffective,

        @NotNull(message = "Commission tax type must not be null")
        CommissionTaxType commissionTaxType,

        @NotNull(message = "Rate must not be null")
        Double rate,

        UUID pointOfSaleId,

        @NotNull(message = "Product ID must not be null")
        UUID productId
) {}

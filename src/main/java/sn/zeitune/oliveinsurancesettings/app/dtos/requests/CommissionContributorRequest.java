package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CommissionContributorRequest(

        @NotNull(message = "Effective date must not be null")
        @PastOrPresent(message = "Effective date cannot be in the future")
        LocalDate dateEffective,

        @NotNull(message = "Commission base must not be null")
        CalculationBase commissionBase,

        @NotNull(message = "Contributor rate must not be null")
        Double contributionRate,

        Double managementRate,

        UUID contributorTypeId,
        UUID contributorId,

        UUID coverageId,

        @NotNull(message = "Product ID must not be null")
        UUID productId
) {}

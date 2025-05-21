package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record DurationRateRequest(

        @NotNull(message = "Effective date must not be null")
        @PastOrPresent(message = "Effective date cannot be in the future")
        LocalDate dateEffective,

        @NotNull(message = "Duration code must not be null")
        String durationCode,

        @NotNull(message = "Rate must not be null")
        Double rate,

        @NotNull(message = "Product ID must not be null")
        UUID productId,

        @NotNull(message = "Company ID must not be null")
        UUID companyId
) {}

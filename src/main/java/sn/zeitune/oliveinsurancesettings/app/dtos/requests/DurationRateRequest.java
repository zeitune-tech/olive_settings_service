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
        LocalDate dateEffective,

        @NotNull(message = "Duration code must not be null")
        UUID durationId,

        @NotNull(message = "Rate must not be null")
        Double rate,

        @NotNull(message = "Product ID must not be null")
        UUID productId
) {}

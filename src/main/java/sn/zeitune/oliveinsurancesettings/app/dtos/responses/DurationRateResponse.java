package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record DurationRateResponse(
        UUID uuid,
        LocalDate dateEffective,
        String durationCode,
        Double rate,
        UUID productId,
        UUID companyId
) {}

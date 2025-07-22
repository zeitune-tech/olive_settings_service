package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record DurationRateResponse(
        UUID id,
        LocalDate dateEffective,
        CoverageDurationResponse duration,
        Double rate,
        ProductResponse product,
        UUID companyId
) {}

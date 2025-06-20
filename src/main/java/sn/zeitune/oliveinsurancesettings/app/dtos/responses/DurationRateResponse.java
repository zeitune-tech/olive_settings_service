package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record DurationRateResponse(
        UUID id,
        LocalDate dateEffective,
        CoverageDurationResponse duration,
        Double rate,
        ProductResponseDTO product,
        UUID companyId
) {}

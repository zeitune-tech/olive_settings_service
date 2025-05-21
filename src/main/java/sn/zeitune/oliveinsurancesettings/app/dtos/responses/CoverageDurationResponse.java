package sn.zeitune.oliveinsurancesettings.app.dto.response;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CoverageDurationType;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CoverageDurationResponse(
        Long id,
        UUID uuid,
        LocalDate from,
        LocalDate to,
        CoverageDurationType type,
        String prorotaMode,
        String unit,
        UUID managementEntity
) {
}

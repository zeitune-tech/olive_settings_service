package sn.zeitune.oliveinsurancesettings.app.dtos.responses;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CoverageDurationType;
import sn.zeitune.oliveinsurancesettings.enums.Unit;

import java.util.UUID;

@Builder
public record CoverageDurationResponse(
        UUID id,
        Double from,
        Double to,
        CoverageDurationType type,
        String prorotaMode,
        Unit unit,
        UUID managementEntity
) {
}

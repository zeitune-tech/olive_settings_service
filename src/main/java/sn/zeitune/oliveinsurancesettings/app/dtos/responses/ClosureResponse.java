package sn.zeitune.oliveinsurancesettings.app.dto.response;


import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;
import sn.zeitune.oliveinsurancesettings.enums.ClosureType;

@Builder
public record ClosureResponse(
        Long id,
        UUID uuid,
        ClosureType type,
        LocalDate date,
        UUID managementEntity
) {}


package sn.zeitune.oliveinsurancesettings.app.dtos.responses;


import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;
import sn.zeitune.oliveinsurancesettings.enums.ClosureType;

@Builder
public record ClosureResponse(
        UUID id,
        ClosureType type,
        LocalDate date,
        UUID managementEntity
) {}


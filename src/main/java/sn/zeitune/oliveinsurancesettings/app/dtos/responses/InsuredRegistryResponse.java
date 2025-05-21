package sn.zeitune.oliveinsurancesettings.app.dtos.responses;
import lombok.Builder;

import java.util.UUID;

@Builder
public record InsuredRegistryResponse(
        UUID id,
        String prefix,
        int length,
        UUID managementEntity,
        int counter
) {}
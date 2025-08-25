package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;

import java.util.List;
import java.util.UUID;

@Builder
public record ProductResponse(
        UUID id,
        String code,
        String name,
        BranchResponseDTO branch,
        String description,
        ManagementEntityResponse owner,
        Integer minRisk,
        Integer maxRisk,
        Integer minimumGuaranteeNumber,
        Boolean fleet,
        boolean hasReduction,
        String visibility,
        List<UUID> sharedWith
) {}

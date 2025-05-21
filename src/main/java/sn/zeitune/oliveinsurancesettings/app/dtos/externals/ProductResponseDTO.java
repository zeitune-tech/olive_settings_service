package sn.zeitune.oliveinsurancesettings.app.dtos.externals;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ProductResponseDTO(
        UUID id,
        String name,
        BranchResponseDTO branch,
        String description,
        UUID ownerId,
        String ownerName,
        Integer minRisk,
        Integer maxRisk,
        Integer minimumGuaranteeNumber,
        Boolean fleet,
        boolean hasReduction,
        String visibility,
        List<UUID> sharedWith
) {}

package sn.zeitune.oliveinsurancesettings.app.dtos.externals;

import java.util.UUID;

public record BranchResponseDTO(
        UUID id,
        String name,
        String description
) {}

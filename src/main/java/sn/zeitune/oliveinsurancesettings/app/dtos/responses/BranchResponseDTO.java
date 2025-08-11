package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import java.util.UUID;

public record BranchResponseDTO(
        UUID id,
        String name,
        String description,
        CategoryResponseDTO category
) {}

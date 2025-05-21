package sn.zeitune.olive_insurance_administration.app.dto.responses;

import java.util.UUID;

public record BranchResponseDTO(
        UUID id,
        String name,
        String description,
        CategoryResponseDTO category
) {}

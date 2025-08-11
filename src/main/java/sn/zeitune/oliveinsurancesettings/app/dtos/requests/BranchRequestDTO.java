package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BranchRequestDTO(
        @NotBlank(message = "Name is mandatory")
        String name,

        String description,

        @NotNull(message = "Category UUID is required")
        UUID categoryUuid
) {}


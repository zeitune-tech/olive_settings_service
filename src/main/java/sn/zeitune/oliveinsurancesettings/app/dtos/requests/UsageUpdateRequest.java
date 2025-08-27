package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UsageUpdateRequest(
        @NotBlank @Size(max = 64)
        @Pattern(regexp = "^[A-Z0-9_]+$") String code,
        @NotBlank @Size(max = 128) String libelle,
        @NotNull Boolean actif,
        @NotNull UUID genre
) {}


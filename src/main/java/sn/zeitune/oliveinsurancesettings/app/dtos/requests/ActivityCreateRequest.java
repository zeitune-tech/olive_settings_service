package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ActivityCreateRequest(
        @NotBlank @Size(max = 64)
        @Pattern(regexp = "^[A-Z0-9_]+$") String code,
        @NotBlank @Size(max = 128) String libelle,
        Boolean actif
) {}


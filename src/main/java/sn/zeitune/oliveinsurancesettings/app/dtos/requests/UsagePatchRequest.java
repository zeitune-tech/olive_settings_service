package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UsagePatchRequest(
        @Nullable @Size(max = 64) @Pattern(regexp = "^[A-Z0-9_]+$") String code,
        @Nullable @Size(max = 128) String libelle,
        @Nullable Boolean actif,
        @Nullable UUID genre
) {}


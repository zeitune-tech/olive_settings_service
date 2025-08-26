package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProfessionResponse(
        UUID uuid,
        String code,
        String libelle,
        boolean actif,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}


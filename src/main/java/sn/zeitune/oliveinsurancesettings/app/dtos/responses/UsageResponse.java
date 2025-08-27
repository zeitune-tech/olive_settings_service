package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsageResponse(
        UUID uuid,
        String code,
        String libelle,
        boolean actif,
        GenreResponse genre,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID id,
        String name,
        String description
) {}

package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;

import java.util.UUID;

public record EndorsementResponse(
        UUID id,
        String name,
        NatureEndorsement nature
) {
}

package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;

public record EndorsementRequest(
    String name,
    NatureEndorsement nature
) {
}

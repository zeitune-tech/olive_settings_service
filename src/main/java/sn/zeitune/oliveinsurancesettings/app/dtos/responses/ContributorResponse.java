package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import java.util.UUID;

public record ContributorResponse(
        UUID id,
        String fullName,
        ContributorTypeResponse contributorType
) {
}

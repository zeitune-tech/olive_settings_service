package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import java.time.LocalDate;
import java.util.UUID;

public record TaxCommissionsContributorResponse(
        UUID id,
        LocalDate dateEffective,
        Double rate,
        Boolean toWithhold,
        UUID managementEntity,
        UUID contributor,
        UUID contributorType
) {
}

package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import java.time.LocalDate;
import java.util.UUID;

public record TaxCommissionsContributorRequest(
        LocalDate dateEffective,
        Double rate,
        Boolean toWithhold,
        UUID managementEntity,
        UUID contributorId,
        UUID contributorTypeId
) {
}

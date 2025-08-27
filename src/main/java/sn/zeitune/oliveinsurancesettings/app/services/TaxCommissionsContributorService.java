package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxCommissionsContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxCommissionsContributorResponse;

import java.util.List;
import java.util.UUID;

public interface TaxCommissionsContributorService {

    TaxCommissionsContributorResponse create(TaxCommissionsContributorRequest request, UUID managementEntity);

    TaxCommissionsContributorResponse update(UUID uuid, TaxCommissionsContributorRequest request);

    TaxCommissionsContributorResponse getByUuid(UUID uuid);

    List<TaxCommissionsContributorResponse> getAll(UUID managementEntity);

    void delete(UUID uuid);
}


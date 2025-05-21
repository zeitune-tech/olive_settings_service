package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxResponse;

import java.util.List;
import java.util.UUID;

public interface TaxService {
    TaxResponse create(TaxRequest request, UUID managementEntity);

    TaxResponse getByUuid(UUID uuid);
    List<TaxResponse> getAll(UUID managementEntity);
    void delete(UUID uuid);
}

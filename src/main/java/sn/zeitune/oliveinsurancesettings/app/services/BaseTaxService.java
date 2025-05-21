package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.BaseTaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.BaseTaxResponse;

import java.util.List;
import java.util.UUID;

public interface BaseTaxService {
    BaseTaxResponse create(BaseTaxRequest request, UUID managementEntity);

    BaseTaxResponse getByUuid(UUID uuid);
    List<BaseTaxResponse> getAll(UUID managementEntity);
    void delete(UUID uuid);
}

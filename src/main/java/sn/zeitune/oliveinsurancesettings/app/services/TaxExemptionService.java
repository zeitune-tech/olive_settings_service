package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxExemptionRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxExemptionResponse;

import java.util.List;
import java.util.UUID;

public interface TaxExemptionService {

    TaxExemptionResponse create(TaxExemptionRequest request, UUID managementEntity);

    TaxExemptionResponse update(UUID uuid, TaxExemptionRequest request);

    TaxExemptionResponse getByUuid(UUID uuid);

    List<TaxExemptionResponse> getAllByManagementEntity(UUID managementEntity);

    void deleteByUuid(UUID uuid);
}

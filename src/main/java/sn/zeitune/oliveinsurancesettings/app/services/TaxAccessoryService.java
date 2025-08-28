package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxAccessoryResponse;

import java.util.List;
import java.util.UUID;

public interface TaxAccessoryService {

    TaxAccessoryResponse create(TaxRequest request, UUID managementEntity);

    TaxAccessoryResponse update(UUID uuid, TaxRequest request);

    TaxAccessoryResponse getByUuid(UUID uuid);

    List<TaxAccessoryResponse> getAllActive(UUID managementEntity);
    List<TaxAccessoryResponse> getAllIncludeDeleted(UUID managementEntity);

    void deleteByUuid(UUID uuid);
}

package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxTypeRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxTypeResponse;

import java.util.List;
import java.util.UUID;

public interface TaxTypeService {

    TaxTypeResponse create(TaxTypeRequest request, UUID managementEntity);

    TaxTypeResponse update(UUID uuid, TaxTypeRequest request);

    TaxTypeResponse getByUuid(UUID uuid);

    List<TaxTypeResponse> getAll();

    void deleteByUuid(UUID uuid);

    List<TaxTypeResponse> getAllByManagementEntity(UUID managementEntity);
}

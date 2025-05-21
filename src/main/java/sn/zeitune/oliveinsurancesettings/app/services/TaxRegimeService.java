package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRegimeRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxRegimeResponse;

import java.util.List;
import java.util.UUID;

public interface TaxRegimeService {

    TaxRegimeResponse create(TaxRegimeRequest request, UUID managementEntity);

    TaxRegimeResponse getByUuid(UUID uuid);
    List<TaxRegimeResponse> getAll();
    void delete(UUID uuid);
}

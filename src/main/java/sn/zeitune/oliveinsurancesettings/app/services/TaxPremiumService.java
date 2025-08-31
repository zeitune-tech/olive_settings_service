package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxPremiumResponse;

import java.util.List;
import java.util.UUID;

public interface TaxPremiumService {

    TaxPremiumResponse create(TaxRequest request, UUID managementEntity);

    TaxPremiumResponse update(UUID uuid, TaxRequest request);

    TaxPremiumResponse getByUuid(UUID uuid);

    List<TaxPremiumResponse> getAllActive(UUID managementEntity);
    List<TaxPremiumResponse> getAllIncludeDeleted(UUID managementEntity);

    void deleteByUuid(UUID uuid);
}

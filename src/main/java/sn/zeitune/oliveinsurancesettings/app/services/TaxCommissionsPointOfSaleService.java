package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxCommissionsPointOfSaleRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxCommissionsPointOfSaleResponse;

import java.util.List;
import java.util.UUID;

public interface TaxCommissionsPointOfSaleService {

    TaxCommissionsPointOfSaleResponse create(TaxCommissionsPointOfSaleRequest request, UUID managementEntity);

    TaxCommissionsPointOfSaleResponse update(UUID uuid, TaxCommissionsPointOfSaleRequest request);

    TaxCommissionsPointOfSaleResponse getByUuid(UUID uuid);

    List<TaxCommissionsPointOfSaleResponse> getAll(UUID managementEntity);

    void delete(UUID uuid);
}

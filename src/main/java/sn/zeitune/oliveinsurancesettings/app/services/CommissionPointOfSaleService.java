package sn.zeitune.oliveinsurancesettings.app.services;

import jakarta.validation.Valid;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionPointOfSaleRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionPointOfSaleResponse;

import java.util.List;
import java.util.UUID;

public interface CommissionPointOfSaleService {
    CommissionPointOfSaleResponse create(CommissionPointOfSaleRequest request, UUID insuranceUuid);
    CommissionPointOfSaleResponse getByUuid(UUID uuid);
    List<CommissionPointOfSaleResponse> getAllActive(UUID managementEntity);
    List<CommissionPointOfSaleResponse> getAllIncludeDeleted(UUID managementEntity);
    void delete(UUID uuid);

    CommissionPointOfSaleResponse update(UUID uuid, @Valid CommissionPointOfSaleRequest request, UUID managementEntity);

    List<CommissionPointOfSaleResponse> getAllActivePrimes(UUID managementEntity);

    List<CommissionPointOfSaleResponse> getAllAccessories(UUID managementEntity);
}

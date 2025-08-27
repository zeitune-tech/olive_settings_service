package sn.zeitune.oliveinsurancesettings.app.services;

import jakarta.validation.Valid;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.AccessoryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.AccessoryResponse;

import java.util.List;
import java.util.UUID;

public interface AccessoryService {
    AccessoryResponse create(AccessoryRequest request, UUID managementEntity);
    AccessoryResponse getByUuid(UUID uuid);
    List<AccessoryResponse> getAllActive(UUID managementEntity);
    void delete(UUID uuid);

    AccessoryResponse update(UUID uuid, AccessoryRequest request, UUID managementEntity);
}

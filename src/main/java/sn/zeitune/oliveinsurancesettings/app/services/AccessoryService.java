package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.AccessoryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.AccessoryResponse;

import java.util.List;
import java.util.UUID;

public interface AccessoryService {
    AccessoryResponse create(AccessoryRequest request, UUID managementEntity);
    AccessoryResponse getByUuid(UUID uuid);
    List<AccessoryResponse> getAll(UUID managementEntity);
    void delete(UUID uuid);
}

package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionResponse;

import java.util.List;
import java.util.UUID;

public interface CommissionService {
    CommissionResponse create(CommissionRequest request, UUID insuranceUuid);
    CommissionResponse getByUuid(UUID uuid);
    List<CommissionResponse> getAll(UUID managementEntity);
    void delete(UUID uuid);
}

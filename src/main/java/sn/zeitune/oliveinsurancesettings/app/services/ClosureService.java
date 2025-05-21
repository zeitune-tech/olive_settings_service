package sn.zeitune.oliveinsurancesettings.app.services;


import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ClosureRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ClosureResponse;

import java.util.List;
import java.util.UUID;

public interface ClosureService {

    ClosureResponse create(ClosureRequest request, UUID managementEntity);

    ClosureResponse getByUuid(UUID uuid);

    List<ClosureResponse> getAll(UUID managementEntity);

    ClosureResponse update(UUID uuid, ClosureRequest request);

    void delete(UUID uuid);
}

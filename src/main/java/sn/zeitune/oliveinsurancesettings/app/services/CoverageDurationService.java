package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageDurationRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageDurationResponse;

import java.util.List;
import java.util.UUID;

public interface CoverageDurationService {

    CoverageDurationResponse create(CoverageDurationRequest request, UUID managementEntity);

    CoverageDurationResponse getByUuid(UUID uuid);

    List<CoverageDurationResponse> getAll(UUID managementEntity);

    CoverageDurationResponse update(UUID uuid, CoverageDurationRequest request);

    void delete(UUID uuid);
}

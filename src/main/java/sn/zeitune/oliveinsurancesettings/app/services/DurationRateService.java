package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.DurationRateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.DurationRateResponse;

import java.util.List;
import java.util.UUID;

public interface DurationRateService {
    DurationRateResponse create(DurationRateRequest request, UUID managementEntity);

    DurationRateResponse getByUuid(UUID uuid);
    List<DurationRateResponse> getAll();
    void delete(UUID uuid);
}

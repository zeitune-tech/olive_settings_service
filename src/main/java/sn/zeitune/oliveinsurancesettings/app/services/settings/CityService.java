package sn.zeitune.oliveinsurancesettings.app.services.settings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CityCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CityPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CityUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CityResponse;

import java.util.UUID;

public interface CityService {
    CityResponse create(CityCreateRequest request);
    CityResponse getByUuid(UUID uuid);
    Page<CityResponse> listAdmin(String q, Boolean actif, Boolean includeDeleted, Pageable pageable);
    CityResponse update(UUID uuid, CityUpdateRequest request);
    CityResponse patch(UUID uuid, CityPatchRequest request);
    void delete(UUID uuid);

    Page<CityResponse> listInterService(String q, Pageable pageable);
}


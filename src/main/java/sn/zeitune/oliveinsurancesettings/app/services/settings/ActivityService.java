package sn.zeitune.oliveinsurancesettings.app.services.settings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ActivityCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ActivityPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ActivityUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ActivityResponse;

import java.util.UUID;

public interface ActivityService {
    ActivityResponse create(ActivityCreateRequest request);
    ActivityResponse getByUuid(UUID uuid);
    Page<ActivityResponse> listAdmin(String q, Boolean actif, Boolean includeDeleted, Pageable pageable);
    ActivityResponse update(UUID uuid, ActivityUpdateRequest request);
    ActivityResponse patch(UUID uuid, ActivityPatchRequest request);
    void delete(UUID uuid);
    Page<ActivityResponse> listInterService(String q, Pageable pageable);
}


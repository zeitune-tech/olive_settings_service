package sn.zeitune.oliveinsurancesettings.app.services.settings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.UsageCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.UsagePatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.UsageUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.UsageResponse;

import java.util.UUID;

public interface UsageService {
    UsageResponse create(UsageCreateRequest request);
    UsageResponse getByUuid(UUID uuid);
    Page<UsageResponse> listAdmin(String q, UUID genre, Boolean actif, Boolean includeDeleted, Pageable pageable);
    UsageResponse update(UUID uuid, UsageUpdateRequest request);
    UsageResponse patch(UUID uuid, UsagePatchRequest request);
    void delete(UUID uuid);
    Page<UsageResponse> listInterService(String q, UUID genre, Pageable pageable);
}


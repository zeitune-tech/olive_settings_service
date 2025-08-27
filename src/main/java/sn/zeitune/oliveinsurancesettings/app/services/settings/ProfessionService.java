package sn.zeitune.oliveinsurancesettings.app.services.settings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProfessionCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProfessionPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProfessionUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProfessionResponse;

import java.util.UUID;

public interface ProfessionService {
    ProfessionResponse create(ProfessionCreateRequest request);
    ProfessionResponse getByUuid(UUID uuid);
    Page<ProfessionResponse> listAdmin(String q, Boolean actif, Boolean includeDeleted, Pageable pageable);
    ProfessionResponse update(UUID uuid, ProfessionUpdateRequest request);
    ProfessionResponse patch(UUID uuid, ProfessionPatchRequest request);
    void delete(UUID uuid);
    Page<ProfessionResponse> listInterService(String q, Pageable pageable);
}


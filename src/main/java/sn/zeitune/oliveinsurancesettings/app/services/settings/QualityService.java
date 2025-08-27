package sn.zeitune.oliveinsurancesettings.app.services.settings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.QualityCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.QualityPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.QualityUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.QualityResponse;

import java.util.UUID;

public interface QualityService {
    QualityResponse create(QualityCreateRequest request);
    QualityResponse getByUuid(UUID uuid);
    Page<QualityResponse> listAdmin(String q, Boolean actif, Boolean includeDeleted, Pageable pageable);
    QualityResponse update(UUID uuid, QualityUpdateRequest request);
    QualityResponse patch(UUID uuid, QualityPatchRequest request);
    void delete(UUID uuid);
    Page<QualityResponse> listInterService(String q, Pageable pageable);
}


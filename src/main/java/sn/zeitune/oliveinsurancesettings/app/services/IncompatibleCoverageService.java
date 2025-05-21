package sn.zeitune.oliveinsurancesettings.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.IncompatibleCoverageRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.IncompatibleCoverageResponse;

import java.util.UUID;

public interface IncompatibleCoverageService {

    IncompatibleCoverageResponse create(IncompatibleCoverageRequest request, UUID managementEntity);

    IncompatibleCoverageResponse getByUuid(UUID uuid);

    Page<IncompatibleCoverageResponse> getAll(UUID managementEntity, Pageable pageable);

    IncompatibleCoverageResponse update(UUID uuid, IncompatibleCoverageRequest request);

    void delete(UUID uuid);
}

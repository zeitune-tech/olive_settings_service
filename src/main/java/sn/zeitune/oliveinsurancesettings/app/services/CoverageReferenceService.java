package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageReferenceRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageReferenceResponse;

import java.util.List;
import java.util.UUID;

public interface CoverageReferenceService {

    List<CoverageReferenceResponse> initCoverageReference(UUID managementEntity);

    CoverageReferenceResponse create(CoverageReferenceRequest request, UUID managementEntity);

    CoverageReferenceResponse getByUuid(UUID uuid);

    List<CoverageReferenceResponse> getAll(UUID managementEntity);

    CoverageReferenceResponse update(UUID uuid, CoverageReferenceRequest request);

    void delete(UUID uuid);
}

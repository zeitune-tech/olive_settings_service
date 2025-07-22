package sn.zeitune.oliveinsurancesettings.app.services;

import jakarta.validation.Valid;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionContributorResponse;

import java.util.List;
import java.util.UUID;

public interface CommissionContributorService {
    CommissionContributorResponse create(CommissionContributorRequest request, UUID managementEntity);
    CommissionContributorResponse getByUuid(UUID uuid);
    List<CommissionContributorResponse> getAll(UUID managementEntity);
    void delete(UUID uuid);

    CommissionContributorResponse update(UUID uuid, CommissionContributorRequest request, UUID managementEntity);
}

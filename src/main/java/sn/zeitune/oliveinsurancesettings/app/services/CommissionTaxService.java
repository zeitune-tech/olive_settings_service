package sn.zeitune.oliveinsurancesettings.app.services;

import jakarta.validation.Valid;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionTaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionTaxResponse;

import java.util.List;
import java.util.UUID;

public interface CommissionTaxService {

    CommissionTaxResponse create(CommissionTaxRequest request, UUID managementEntity);
    CommissionTaxResponse getByUuid(UUID uuid);

    List<CommissionTaxResponse> getAll(UUID managementEntity);

    void delete(UUID uuid);

    CommissionTaxResponse update(UUID uuid, @Valid CommissionTaxRequest request, UUID managementEntity);
}

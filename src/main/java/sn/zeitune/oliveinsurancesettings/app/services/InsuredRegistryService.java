package sn.zeitune.oliveinsurancesettings.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.InsuredRegistryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.InsuredRegistryResponse;

import java.util.List;
import java.util.UUID;

public interface InsuredRegistryService {

    InsuredRegistryResponse create(InsuredRegistryRequest request, UUID managementEntity);

    InsuredRegistryResponse getByUuid(UUID uuid);

    Page<InsuredRegistryResponse> getAll(UUID managementEntity, Pageable pageable);

    InsuredRegistryResponse update(UUID uuid, InsuredRegistryRequest request);

    void delete(UUID uuid);
}

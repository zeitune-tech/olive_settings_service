package sn.zeitune.oliveinsurancesettings.app.clients;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AdministrationClient {

    List<ProductResponseDTO> getByManagementEntity(UUID uuid);
    List<ManagementEntityResponse> getManagementEntities(List<UUID> uuids);

    List<ProductResponseDTO> getProductsByIds(List<UUID> uuids);

    Optional<ManagementEntityResponse> findManagementEntityByUuid(UUID ownerUuid);

    List<ManagementEntityResponse> findManagementEntityByUuidIn(Set<UUID> companyUuids);
}

package sn.zeitune.oliveinsurancesettings.app.clients;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

public interface AdministrationClient {

    List<ProductResponseDTO> getByManagementEntity(UUID uuid);
}

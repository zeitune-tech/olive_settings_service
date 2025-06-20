package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.AccessoryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.AccessoryResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Accessory;

public class AccessoryMapper {

    public static Accessory map(AccessoryRequest request) {
        return Accessory.builder()
                .dateEffective(request.dateEffective())
                .actType(request.actType())
                .accessoryAmount(request.accessoryAmount())
                .build();
    }

    public static AccessoryResponse map(Accessory accessory, ProductResponseDTO product) {
        return AccessoryResponse.builder()
                .id(accessory.getUuid())
                .dateEffective(accessory.getDateEffective())
                .actType(accessory.getActType())
                .accessoryAmount(accessory.getAccessoryAmount())
                .product(product)
                .managementEntityId(accessory.getManagementEntity())
                .build();
    }
}

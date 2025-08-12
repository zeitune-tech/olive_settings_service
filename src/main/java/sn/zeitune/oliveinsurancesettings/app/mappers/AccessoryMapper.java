package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.responses.EndorsementResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.AccessoryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.AccessoryResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Accessory;

public class AccessoryMapper {

    public static Accessory map(AccessoryRequest request) {
        return Accessory.builder()
                .dateEffective(request.dateEffective())
                .accessoryAmount(request.accessoryAmount())
                .accessoryRisk(request.accessoryRisk())
                .day(request.day())
                .hour(request.hour())
                .minute(request.minute())
                .build();
    }

    public static AccessoryResponse map(Accessory accessory, ProductResponse product, EndorsementResponse endorsement) {
        return AccessoryResponse.builder()
                .id(accessory.getUuid())
                .dateEffective(accessory.getDateEffective())
                .actType(endorsement)
                .accessoryAmount(accessory.getAccessoryAmount())
                .accessoryRisk(accessory.getAccessoryRisk())
                .day(accessory.getDay())
                .hour(accessory.getHour())
                .minute(accessory.getMinute())
                .product(product)
                .managementEntityId(accessory.getManagementEntity())
                .build();
    }
}

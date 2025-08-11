package sn.zeitune.oliveinsurancesettings.app.dtos.externals;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.ManagementEntityType;

import java.util.UUID;

@Builder
public record ManagementEntityResponse(
        UUID id,
        String name,
        String acronym,
        String email,
        String phone,
        String address,
        String logo,
        String fax,
        String gsm,
        ManagementEntityType type,
        ManagementEntityResponse superiorEntity

) {
}

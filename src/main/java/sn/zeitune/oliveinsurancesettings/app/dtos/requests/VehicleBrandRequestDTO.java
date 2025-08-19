package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import java.util.List;
import java.util.UUID;

public record VehicleBrandRequestDTO (
        String name,

        List<UUID> modelIds
    ) {

}

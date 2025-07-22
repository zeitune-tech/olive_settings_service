package sn.zeitune.oliveinsurancesettings.app.dtos.responses;


import sn.zeitune.oliveinsurancesettings.enums.PointOfSaleType;

import java.time.LocalDate;
import java.util.UUID;

public record TaxCommissionsPointOfSaleResponse(
        UUID id,
        LocalDate dateEffective,
        Double rate,
        Boolean toWithhold,
        UUID managementEntity,
        UUID pointOfSale,
        PointOfSaleType pointOfSaleType
) {
}

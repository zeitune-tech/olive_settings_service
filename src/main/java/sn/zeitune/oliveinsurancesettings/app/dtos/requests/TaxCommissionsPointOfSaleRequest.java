package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import sn.zeitune.oliveinsurancesettings.enums.PointOfSaleType;

import java.time.LocalDate;
import java.util.UUID;

public record TaxCommissionsPointOfSaleRequest(
        LocalDate dateEffective,
        Double rate,
        Boolean toWithhold,
        UUID managementEntity,
        UUID pointOfSaleId,
        PointOfSaleType pointOfSaleType
) {
}

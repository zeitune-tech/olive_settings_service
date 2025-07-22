package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import sn.zeitune.oliveinsurancesettings.enums.TaxNature;

public record TaxTypeRequest (
        String name,
        TaxNature nature
) {
}

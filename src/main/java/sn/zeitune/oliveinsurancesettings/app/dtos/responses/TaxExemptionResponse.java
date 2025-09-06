package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.Tax;

import java.util.Set;
import java.util.UUID;

@Builder
public record TaxExemptionResponse (
        UUID id,
        String name,
        Set<TaxTypeResponse> taxes,
        ProductResponse product
) {
}

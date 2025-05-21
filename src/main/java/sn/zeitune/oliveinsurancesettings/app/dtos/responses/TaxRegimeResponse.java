package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.RegimeNature;

import java.util.Set;
import java.util.UUID;

@Builder
public record TaxRegimeResponse(
        UUID id,
        String designation,
        RegimeNature nature,
        boolean stampExemption,
        Set<TaxResponse> exemptedTaxes
) {}

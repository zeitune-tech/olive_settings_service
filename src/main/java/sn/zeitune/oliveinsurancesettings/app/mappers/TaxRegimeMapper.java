package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRegimeRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxRegimeResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.TaxRegime;
import sn.zeitune.oliveinsurancesettings.app.entities.Tax;

import java.util.Set;
import java.util.stream.Collectors;

public class TaxRegimeMapper {

    public static TaxRegime map(TaxRegimeRequest request, Set<Tax> exemptedTaxes) {
        return TaxRegime.builder()
                .designation(request.designation())
                .nature(request.nature())
                .stampExemption(request.stampExemption())
                .exemptedTaxes(exemptedTaxes)
                .build();
    }

    public static TaxRegimeResponse map(TaxRegime regime) {
        return TaxRegimeResponse.builder()
                .id(regime.getUuid())
                .designation(regime.getDesignation())
                .nature(regime.getNature())
                .stampExemption(regime.isStampExemption())
                .exemptedTaxes(
                        regime.getExemptedTaxes().stream()
                                .map(TaxMapper::map)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}

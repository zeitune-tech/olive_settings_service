package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Tax;

public class TaxMapper {

    public static Tax map(TaxRequest request) {
        return Tax.builder()
                .designation(request.designation())
                .rgr(request.rgr())
                .nature(request.nature())
                .build();
    }

    public static TaxResponse map(Tax tax) {
        return TaxResponse.builder()
                .id(tax.getUuid())
                .designation(tax.getDesignation())
                .rgr(tax.getRgr())
                .nature(tax.getNature())
                .build();
    }
}

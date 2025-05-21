package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.TaxGroup;
import sn.zeitune.oliveinsurancesettings.enums.TaxNature;

import java.util.UUID;

@Builder
public record TaxResponse(
        UUID id,
        String designation,
        TaxGroup rgr,
        TaxNature nature
) {}

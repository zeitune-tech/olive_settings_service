package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.TaxGroup;
import sn.zeitune.oliveinsurancesettings.enums.TaxNature;

import java.util.UUID;

@Builder
public record TaxTypeResponse(
        UUID id,
        String name,
        TaxNature nature
) {}

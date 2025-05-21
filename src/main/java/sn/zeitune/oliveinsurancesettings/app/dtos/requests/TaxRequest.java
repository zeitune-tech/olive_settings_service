package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.TaxGroup;
import sn.zeitune.oliveinsurancesettings.enums.TaxNature;

@Builder
public record TaxRequest(

        @NotBlank(message = "Designation must not be blank")
        String designation,

        @NotNull(message = "Tax group must not be null")
        TaxGroup rgr,

        @NotNull(message = "Tax nature must not be null")
        TaxNature nature
) {}

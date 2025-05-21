package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.RegimeNature;

import java.util.Set;
import java.util.UUID;

@Builder
public record TaxRegimeRequest(

        @NotBlank(message = "Designation must not be blank")
        String designation,

        @NotNull(message = "Regime nature must not be null")
        RegimeNature nature,

        boolean stampExemption,

        @NotNull(message = "Exempted taxes must not be null")
        Set<UUID> exemptedTaxIds
) {}

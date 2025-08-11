package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.TaxGroup;
import sn.zeitune.oliveinsurancesettings.enums.TaxNature;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record TaxRequest(

        @NotBlank(message = "Tax name must not be blank")
        String name,

        @NotNull(message = "Effective date must not be null")
        LocalDate dateEffective,
        @NotNull(message = "Rate must not be null")
        Double rate,

        @NotNull(message = "Tax Type ID must not be null")
        UUID taxTypeId,

        Boolean isFlatRate,

        Double flatRateAmount,

        @NotNull(message = "Coverage ID must not be null")
        UUID coverageId,
        @NotNull(message = "Product ID must not be null")
        UUID productId
) {}

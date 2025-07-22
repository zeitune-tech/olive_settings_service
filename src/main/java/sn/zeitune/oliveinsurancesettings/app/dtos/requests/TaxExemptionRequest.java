package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record TaxExemptionRequest (

        @NotNull(message = "Name must not be null")
        String name,

        @NotNull(message = "Product ID must not be null")
        UUID productId,
        List<UUID> taxIds
) {
}

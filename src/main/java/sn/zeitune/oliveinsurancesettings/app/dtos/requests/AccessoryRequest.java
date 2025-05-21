package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;
import sn.zeitune.oliveinsurancesettings.enums.AccessoryActType;

@Builder
public record AccessoryRequest(

        @NotNull(message = "Effective date must not be null")
        @PastOrPresent(message = "Effective date cannot be in the future")
        LocalDate dateEffective,

        @NotNull(message = "Act type must not be null")
        AccessoryActType actType,

        @NotNull(message = "Accessory amount must not be null")
        Double accessoryAmount,

        @NotNull(message = "Product ID must not be null")
        UUID productId
) {}

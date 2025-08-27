package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.Endorsement;
import sn.zeitune.oliveinsurancesettings.enums.AccessoryActType;

@Builder
public record AccessoryRequest(

        @FutureOrPresent(message = "Effective date cannot be in the past")
        @NotNull(message = "Effective date must not be null")
        LocalDate dateEffective,

        @NotNull(message = "Act type ID must not be null")
        UUID actType,

        @NotNull(message = "Accessory amount must not be null")
        Double accessoryAmount,

        @NotNull(message = "Accessory risk must not be null")
        Double accessoryRisk,

        @NotNull(message = "Day must not be null")
        Integer day,

        @NotNull(message = "Hour must not be null")
        Integer hour,

        @NotNull(message = "Minute must not be null")
        Integer minute,

        @NotNull(message = "Product ID must not be null")
        UUID productId
) {}

package sn.zeitune.oliveinsurancesettings.app.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;
import sn.zeitune.oliveinsurancesettings.enums.ClosureType;

@Builder
public record ClosureRequest(

        @NotNull(message = "Closure type must not be null")
        ClosureType type,

        @NotNull(message = "Effective date must not be null")
        @PastOrPresent(message = "Effective date cannot be in the future")
        LocalDate date,

        @NotNull(message = "Management entity ID must not be null")
        UUID managementEntity

) {}


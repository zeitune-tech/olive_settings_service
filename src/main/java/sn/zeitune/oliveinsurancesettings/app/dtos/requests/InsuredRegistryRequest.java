package sn.zeitune.oliveinsurancesettings.app.dtos.requests;


import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.UUID;

@Builder
public record InsuredRegistryRequest(

        @NotBlank(message = "Prefix must not be blank")
        String prefix,

        @Min(value = 1, message = "Length must be at least 1")
        int length

) {}

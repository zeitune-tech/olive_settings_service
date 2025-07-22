package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TaxResponse(
        UUID id,
        String name,
        Double rate,
        String type
) {
}

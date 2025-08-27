package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CoverageReferenceResponse(

        UUID id,
        String designation,
        String family,
        boolean accessCharacteristic,
        boolean tariffAccess,
        boolean toShareOut,
        UUID managementEntity
) {}

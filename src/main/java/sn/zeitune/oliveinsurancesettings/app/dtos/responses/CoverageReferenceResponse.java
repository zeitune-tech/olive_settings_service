package sn.zeitune.oliveinsurancesettings.app.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CoverageReferenceResponse(

        Long id,
        UUID uuid,
        String designation,
        String family,
        boolean accessCharacteristic,
        boolean tariffAccess,
        UUID managementEntity

) {}

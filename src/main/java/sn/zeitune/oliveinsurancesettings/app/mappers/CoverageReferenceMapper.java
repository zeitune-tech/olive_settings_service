package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageReferenceRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageReferenceResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageReference;

public class CoverageReferenceMapper {

    public static CoverageReference map(CoverageReferenceRequest request) {
        return CoverageReference.builder()
                .designation(request.designation())
                .family(request.family())
                .accessCharacteristic(request.accessCharacteristic())
                .tariffAccess(request.tariffAccess())
                .toShareOut(request.toShareOut())
                .build();
    }

    public static CoverageReferenceResponse map(CoverageReference reference) {

        if (reference == null) {
            return null;
        }
        return CoverageReferenceResponse.builder()
                .id(reference.getUuid())
                .designation(reference.getDesignation())
                .family(reference.getFamily())
                .accessCharacteristic(reference.isAccessCharacteristic())
                .tariffAccess(reference.isTariffAccess())
                .managementEntity(reference.getManagementEntity())
                .toShareOut(reference.isToShareOut())
                .build();
    }
}

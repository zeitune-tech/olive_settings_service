package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.EndorsementRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.EndorsementResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.Endorsement;

import java.util.List;
import java.util.stream.Collectors;

public class EndorsementMapper {

    public static Endorsement map(EndorsementRequest request) {
        return Endorsement.builder()
                .name(request.name())
                .nature(request.nature())
                .build();
    }

    public static EndorsementResponse map(Endorsement endorsement) {
        List<ProductResponse> productResponses = null;

        if (endorsement.getProduct() != null) {
            productResponses = endorsement.getProduct().stream()
                    .map(ProductMapper::map) // tu dois avoir cette méthode dans ProductMapper
                    .collect(Collectors.toList());
        }

        return new EndorsementResponse(
                endorsement.getUuid(),
                endorsement.getName(),
                endorsement.getNature(),
                productResponses
        );
    }
}



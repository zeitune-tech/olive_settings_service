package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductAssignmentRequest {
    private List<UUID> productIds;
}

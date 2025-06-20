package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import java.util.Set;
import java.util.UUID;

public record ProductCoveragesUpdate (
        Set<UUID> coverages
) {
}

package sn.zeitune.oliveinsurancesettings.app.dtos.externals;

import java.util.Set;
import java.util.UUID;

public record CoveragesRequest(
        UUID product,
        Set<UUID> coverages,
        UUID managementEntity
) {
}

// responses
package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;
import java.util.List;
import java.util.Map;

public record SuccessionConfigResponse(
        Map<NatureEndorsement, List<NatureEndorsement>> rules,
        Map<NatureEndorsement, Integer> ranks
) {}

// requests
package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;
import java.util.List;
import java.util.Map;

public record SuccessionConfigRequest(
        Map<NatureEndorsement, List<NatureEndorsement>> rules,
        Map<NatureEndorsement, Integer> ranks
) {}

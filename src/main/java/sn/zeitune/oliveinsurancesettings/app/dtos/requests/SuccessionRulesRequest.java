package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;
import java.util.List;
import java.util.Map;

public record SuccessionRulesRequest(
        Map<NatureEndorsement, List<NatureEndorsement>> rules
) {}

package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;

import java.util.List;
import java.util.Map;

public interface EndorsementSuccessionService {
    Map<NatureEndorsement, List<NatureEndorsement>> getRules();
    Map<NatureEndorsement, List<NatureEndorsement>> saveRules(Map<NatureEndorsement, List<NatureEndorsement>> input);
    Map<NatureEndorsement, List<NatureEndorsement>> defaultRules();
}

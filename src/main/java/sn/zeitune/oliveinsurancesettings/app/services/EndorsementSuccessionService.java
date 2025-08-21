// sn/zeitune/oliveinsurancesettings/app/services/EndorsementSuccessionService.java
package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.SuccessionConfigRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.SuccessionConfigResponse;

public interface EndorsementSuccessionService {
    SuccessionConfigResponse getConfig();
    SuccessionConfigResponse saveConfig(SuccessionConfigRequest request);
    SuccessionConfigResponse defaultConfig();
}

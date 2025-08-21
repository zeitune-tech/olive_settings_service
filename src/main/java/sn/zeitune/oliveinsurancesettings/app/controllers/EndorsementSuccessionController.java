// sn/zeitune/oliveinsurancesettings/app/controllers/EndorsementSuccessionController.java
package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.SuccessionConfigRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.SuccessionConfigResponse;
import sn.zeitune.oliveinsurancesettings.app.services.EndorsementSuccessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/endorsements")
public class EndorsementSuccessionController {

    private final EndorsementSuccessionService service;

    @GetMapping("/succession-rules")
    public ResponseEntity<SuccessionConfigResponse> getConfig() {
        return ResponseEntity.ok(service.getConfig());
    }

    @PutMapping("/succession-rules")
    public ResponseEntity<SuccessionConfigResponse> saveConfig(@RequestBody SuccessionConfigRequest request) {
        return ResponseEntity.ok(service.saveConfig(request));
    }

    @GetMapping("/succession-rules/defaults")
    public ResponseEntity<SuccessionConfigResponse> getDefaults() {
        return ResponseEntity.ok(service.defaultConfig());
    }
}

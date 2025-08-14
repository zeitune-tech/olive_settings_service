package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.SuccessionRulesRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.SuccessionRulesResponse;
import sn.zeitune.oliveinsurancesettings.app.services.EndorsementSuccessionService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/endorsements")
public class EndorsementSuccessionController {

    private final EndorsementSuccessionService service;

    @GetMapping("/succession-rules")
    public ResponseEntity<SuccessionRulesResponse> getRules() {
        return ResponseEntity.ok(new SuccessionRulesResponse(service.getRules()));
    }

    @PutMapping("/succession-rules")
    public ResponseEntity<SuccessionRulesResponse> saveRules(@RequestBody SuccessionRulesRequest request) {
        return ResponseEntity.ok(new SuccessionRulesResponse(service.saveRules(request.rules())));
    }

    @GetMapping("/succession-rules/defaults")
    public ResponseEntity<SuccessionRulesResponse> getDefaults() {
        return ResponseEntity.ok(new SuccessionRulesResponse(service.defaultRules()));
    }
}

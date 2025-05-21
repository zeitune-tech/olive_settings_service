package sn.zeitune.oliveinsurancesettings.app.controllers.interservice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.CoveragesRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageReferenceResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageResponse;
import sn.zeitune.oliveinsurancesettings.app.services.CoverageReferenceService;
import sn.zeitune.oliveinsurancesettings.app.services.CoverageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inter-services/coverages")
@RequiredArgsConstructor
public class CoverageInterServiceController {

    private final CoverageService coverageService;
    private final CoverageReferenceService coverageReferenceService;

    @PostMapping
    public ResponseEntity<List<CoverageResponse>> createCoverages(
            @Valid @RequestBody CoveragesRequest request
    ) {

        List<CoverageResponse> coverageResponses = coverageService.createCoverages(
                request.managementEntity(),
                request
        );
        return ResponseEntity.ok(coverageResponses);
    }

    @PostMapping("/init/{uuid}")
    public ResponseEntity<List<CoverageReferenceResponse>> initCoverage(
            @PathVariable UUID uuid
    ) {
        List<CoverageReferenceResponse> coverages = coverageReferenceService.initCoverageReference(uuid);
        return ResponseEntity.ok(coverages);
    }
}

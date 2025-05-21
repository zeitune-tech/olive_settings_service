package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageReferenceRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageReferenceResponse;
import sn.zeitune.oliveinsurancesettings.app.services.CoverageReferenceService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/coverage-references")
@RequiredArgsConstructor
public class CoverageReferenceController {

    private final CoverageReferenceService coverageReferenceService;

    @PostMapping
    public ResponseEntity<CoverageReferenceResponse> create(
            @RequestBody @Valid CoverageReferenceRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        CoverageReferenceResponse response = coverageReferenceService.create(
            request,
            employee.getManagementEntity()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CoverageReferenceResponse> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(coverageReferenceService.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<CoverageReferenceResponse>> getAll(
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(coverageReferenceService.getAll(
            employee.getManagementEntity()
        ));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CoverageReferenceResponse> update(@PathVariable UUID uuid,
                                                            @RequestBody @Valid CoverageReferenceRequest request) {
        return ResponseEntity.ok(coverageReferenceService.update(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        coverageReferenceService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

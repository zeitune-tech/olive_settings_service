package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageDurationRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageDurationResponse;
import sn.zeitune.oliveinsurancesettings.app.services.CoverageDurationService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/coverage-durations")
@RequiredArgsConstructor
public class CoverageDurationController {

    private final CoverageDurationService coverageDurationService;

    @PostMapping
    public ResponseEntity<CoverageDurationResponse> create(
            @RequestBody @Valid CoverageDurationRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        CoverageDurationResponse response = coverageDurationService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CoverageDurationResponse> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(coverageDurationService.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<CoverageDurationResponse>> getAll(
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(coverageDurationService.getAll(
                employee.getManagementEntity()
        ));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CoverageDurationResponse> update(@PathVariable UUID uuid,
                                                           @RequestBody @Valid CoverageDurationRequest request) {
        return ResponseEntity.ok(coverageDurationService.update(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        coverageDurationService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

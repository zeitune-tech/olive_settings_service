package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.IncompatibleCoverageRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.IncompatibleCoverageResponse;
import sn.zeitune.oliveinsurancesettings.app.services.IncompatibleCoverageService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/incompatible-coverages")
@RequiredArgsConstructor
public class IncompatibleCoverageController {

    private final IncompatibleCoverageService incompatibleCoverageService;

    @PostMapping
    public ResponseEntity<IncompatibleCoverageResponse> create(
            @RequestBody @Valid IncompatibleCoverageRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        IncompatibleCoverageResponse response = incompatibleCoverageService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<IncompatibleCoverageResponse> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(incompatibleCoverageService.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<Page<IncompatibleCoverageResponse>> getAll(
            Authentication authentication,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(incompatibleCoverageService.getAll(
                employee.getManagementEntity(),
                pageable
        ));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<IncompatibleCoverageResponse> update(@PathVariable UUID uuid,
                                                               @RequestBody @Valid IncompatibleCoverageRequest request) {
        return ResponseEntity.ok(incompatibleCoverageService.update(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        incompatibleCoverageService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxCommissionsContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxCommissionsContributorResponse;
import sn.zeitune.oliveinsurancesettings.app.services.TaxCommissionsContributorService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/tax-commissions/contributors")
@RequiredArgsConstructor
public class TaxCommissionsContributorController {

    private final TaxCommissionsContributorService service;

    @PostMapping
    public ResponseEntity<TaxCommissionsContributorResponse> create(
            @RequestBody TaxCommissionsContributorRequest request,
                        Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<TaxCommissionsContributorResponse> update(
            @PathVariable UUID uuid,
            @RequestBody TaxCommissionsContributorRequest request,
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        return ResponseEntity.ok(service.update(uuid, request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TaxCommissionsContributorResponse> getByUuid(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<TaxCommissionsContributorResponse>> getAll(
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        return ResponseEntity.ok(service.getAll(managementEntity));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

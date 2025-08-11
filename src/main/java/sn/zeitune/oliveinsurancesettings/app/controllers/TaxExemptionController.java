package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxExemptionRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxExemptionResponse;
import sn.zeitune.oliveinsurancesettings.app.services.TaxExemptionService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/taxes/exemptions")
@RequiredArgsConstructor
public class TaxExemptionController {

    private final TaxExemptionService taxExemptionService;

    @PostMapping
    public ResponseEntity<TaxExemptionResponse> create(
            @RequestBody TaxExemptionRequest request,
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        TaxExemptionResponse response = taxExemptionService.create(request, managementEntity);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<TaxExemptionResponse> update(
            @PathVariable UUID uuid,
            @RequestBody TaxExemptionRequest request,
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        TaxExemptionResponse response = taxExemptionService.update(uuid, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TaxExemptionResponse> getByUuid(@PathVariable UUID uuid) {
        TaxExemptionResponse response = taxExemptionService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaxExemptionResponse>> getAllByManagementEntity(
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        List<TaxExemptionResponse> responses = taxExemptionService.getAllByManagementEntity(managementEntity);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        taxExemptionService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}

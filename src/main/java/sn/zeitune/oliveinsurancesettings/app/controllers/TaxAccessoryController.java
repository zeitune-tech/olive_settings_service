package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxAccessoryResponse;
import sn.zeitune.oliveinsurancesettings.app.services.TaxAccessoryService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/taxes-accessories")
@RequiredArgsConstructor
public class TaxAccessoryController {

    private final TaxAccessoryService taxAccessoryService;

    @PostMapping
    public ResponseEntity<TaxAccessoryResponse> create(
            @RequestBody TaxRequest request,
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        TaxAccessoryResponse response = taxAccessoryService.create(request, managementEntity);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<TaxAccessoryResponse> update(
            @PathVariable UUID uuid,
            @RequestBody TaxRequest request,
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        TaxAccessoryResponse response = taxAccessoryService.update(uuid, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TaxAccessoryResponse> getByUuid(@PathVariable UUID uuid) {
        TaxAccessoryResponse response = taxAccessoryService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaxAccessoryResponse>> getAllByManagementEntity(
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        List<TaxAccessoryResponse> responses = taxAccessoryService.getAllByManagementEntity(managementEntity);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        taxAccessoryService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}

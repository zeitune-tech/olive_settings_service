package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxCommissionsPointOfSaleRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxCommissionsPointOfSaleResponse;
import sn.zeitune.oliveinsurancesettings.app.services.TaxCommissionsPointOfSaleService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/tax-commissions/point-of-sale")
@RequiredArgsConstructor
public class TaxCommissionsPointOfSaleController {

    private final TaxCommissionsPointOfSaleService service;

    @PostMapping
    public ResponseEntity<TaxCommissionsPointOfSaleResponse> create(
            @RequestBody TaxCommissionsPointOfSaleRequest request,
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<TaxCommissionsPointOfSaleResponse> update(
            @PathVariable UUID uuid,
            @RequestBody TaxCommissionsPointOfSaleRequest request,
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        return ResponseEntity.ok(service.update(uuid, request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TaxCommissionsPointOfSaleResponse> getByUuid(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<TaxCommissionsPointOfSaleResponse>> getAll(
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

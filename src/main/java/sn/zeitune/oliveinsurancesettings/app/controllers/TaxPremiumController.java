package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxPremiumResponse;
import sn.zeitune.oliveinsurancesettings.app.services.TaxPremiumService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/taxes-primes")
@RequiredArgsConstructor
public class TaxPremiumController {

    private final TaxPremiumService taxPremiumService;

    @PostMapping
    public ResponseEntity<TaxPremiumResponse> create(
            @RequestBody TaxRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        TaxPremiumResponse response = taxPremiumService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<TaxPremiumResponse> update(
            @PathVariable UUID uuid,
            @RequestBody TaxRequest request) {
        TaxPremiumResponse response = taxPremiumService.update(uuid, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TaxPremiumResponse> getByUuid(@PathVariable UUID uuid) {
        TaxPremiumResponse response = taxPremiumService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaxPremiumResponse>> getAllByManagementEntity(
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        List<TaxPremiumResponse> responses = taxPremiumService.getAllActive(managementEntity);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        taxPremiumService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}

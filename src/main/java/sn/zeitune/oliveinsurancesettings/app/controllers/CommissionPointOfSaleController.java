package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionPointOfSaleRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionPointOfSaleResponse;
import sn.zeitune.oliveinsurancesettings.app.services.CommissionPointOfSaleService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/commissions-points-of-sale")
@RequiredArgsConstructor
public class CommissionPointOfSaleController {

    private final CommissionPointOfSaleService commissionPointOfSaleService;

    @PostMapping
    public ResponseEntity<CommissionPointOfSaleResponse> create(
            @RequestBody CommissionPointOfSaleRequest request,
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        CommissionPointOfSaleResponse response =
                commissionPointOfSaleService.create(request, managementEntity);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CommissionPointOfSaleResponse> update(
            @PathVariable UUID uuid,
            @RequestBody CommissionPointOfSaleRequest request,
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        CommissionPointOfSaleResponse response =
                commissionPointOfSaleService.update(uuid, request, managementEntity);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CommissionPointOfSaleResponse> getByUuid(@PathVariable UUID uuid) {
        CommissionPointOfSaleResponse response =
                commissionPointOfSaleService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CommissionPointOfSaleResponse>> getAll(
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        List<CommissionPointOfSaleResponse> responses =
                commissionPointOfSaleService.getAll(managementEntity);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/primes")
    public ResponseEntity<List<CommissionPointOfSaleResponse>> getAllPrimes(
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        List<CommissionPointOfSaleResponse> responses =
                commissionPointOfSaleService.getAllPrimes(managementEntity);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/accessories")
    public ResponseEntity<List<CommissionPointOfSaleResponse>> getAllAccessories(
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        List<CommissionPointOfSaleResponse> responses =
                commissionPointOfSaleService.getAllAccessories(managementEntity);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        commissionPointOfSaleService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

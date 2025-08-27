package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionContributorResponse;
import sn.zeitune.oliveinsurancesettings.app.services.CommissionContributorService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/commissions-contributors")
@RequiredArgsConstructor
public class CommissionContributorController {

    private final CommissionContributorService commissionContributorService;

    @PostMapping
    public ResponseEntity<CommissionContributorResponse> create(
            @RequestBody CommissionContributorRequest request,
            Authentication authentication
            ) {

        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        CommissionContributorResponse response =
                commissionContributorService.create(request, managementEntity);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CommissionContributorResponse> update(
            @PathVariable UUID uuid,
            @RequestBody CommissionContributorRequest request,
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        CommissionContributorResponse response =
                commissionContributorService.update(uuid, request, managementEntity);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CommissionContributorResponse>> getAll(
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        List<CommissionContributorResponse> responses =
                commissionContributorService.getAll(managementEntity);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/primes")
    public ResponseEntity<List<CommissionContributorResponse>> getAllPrimes(
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        List<CommissionContributorResponse> responses =
                commissionContributorService.getAllPrimes(managementEntity);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/accessories")
    public ResponseEntity<List<CommissionContributorResponse>> getAllAccessories(
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        List<CommissionContributorResponse> responses =
                commissionContributorService.getAllAccessories(managementEntity);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CommissionContributorResponse> getByUuid(@PathVariable UUID uuid) {
        CommissionContributorResponse response =
                commissionContributorService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        commissionContributorService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

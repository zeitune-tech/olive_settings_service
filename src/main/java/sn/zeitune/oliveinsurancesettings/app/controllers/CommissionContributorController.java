package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
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
@RequestMapping("/app/commission-contributors")
@RequiredArgsConstructor
public class CommissionContributorController {

    private final CommissionContributorService commissionContributorService;

    @PostMapping
    public ResponseEntity<CommissionContributorResponse> create(
            @RequestBody @Valid CommissionContributorRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        CommissionContributorResponse response = commissionContributorService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CommissionContributorResponse> update(
            @PathVariable UUID uuid,
            @RequestBody @Valid CommissionContributorRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        CommissionContributorResponse response = commissionContributorService.update(uuid, request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<CommissionContributorResponse> getByUuid(@PathVariable UUID uuid) {
        CommissionContributorResponse response = commissionContributorService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CommissionContributorResponse>> getAll(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        List<CommissionContributorResponse> responses = commissionContributorService.getAll(employee.getManagementEntity());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        commissionContributorService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

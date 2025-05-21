package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionTaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionTaxResponse;
import sn.zeitune.oliveinsurancesettings.app.services.CommissionTaxService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/commission-taxes")
@RequiredArgsConstructor
public class CommissionTaxController {

    private final CommissionTaxService commissionTaxService;

    @PostMapping
    public ResponseEntity<CommissionTaxResponse> create(
            @RequestBody @Valid CommissionTaxRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        CommissionTaxResponse response = commissionTaxService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CommissionTaxResponse> getByUuid(@PathVariable UUID uuid) {
        CommissionTaxResponse response = commissionTaxService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CommissionTaxResponse>> getAll(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        List<CommissionTaxResponse> responses = commissionTaxService.getAll(employee.getManagementEntity());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        commissionTaxService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxTypeRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxTypeResponse;
import sn.zeitune.oliveinsurancesettings.app.services.TaxTypeService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/tax-types")
@RequiredArgsConstructor
public class TaxTypeController {

    private final TaxTypeService taxTypeService;

    @PostMapping
    public ResponseEntity<TaxTypeResponse> create(
            @RequestBody TaxTypeRequest request,
            Authentication authentication
            ) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        TaxTypeResponse response = taxTypeService.create(request, managementEntity);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<TaxTypeResponse> update(
            @PathVariable UUID uuid,
            @RequestBody TaxTypeRequest request,
            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        TaxTypeResponse response = taxTypeService.update(uuid, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TaxTypeResponse> getByUuid(@PathVariable UUID uuid) {
        TaxTypeResponse response = taxTypeService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaxTypeResponse>> getAll(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        UUID managementEntity = employee.getManagementEntity();
        List<TaxTypeResponse> responses = taxTypeService.getAllByManagementEntity(managementEntity);
        return ResponseEntity.ok(responses);
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        taxTypeService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}

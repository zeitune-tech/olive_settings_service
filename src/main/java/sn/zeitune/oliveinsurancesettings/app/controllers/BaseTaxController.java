package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.BaseTaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.BaseTaxResponse;
import sn.zeitune.oliveinsurancesettings.app.services.BaseTaxService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/base-taxes")
@RequiredArgsConstructor
public class BaseTaxController {

    private final BaseTaxService baseTaxService;

    @PostMapping
    public ResponseEntity<BaseTaxResponse> create(
            @RequestBody @Valid BaseTaxRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        BaseTaxResponse response = baseTaxService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<BaseTaxResponse> update(
            @PathVariable UUID uuid,
            @RequestBody @Valid BaseTaxRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        BaseTaxResponse response = baseTaxService.update(uuid, request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<BaseTaxResponse> getByUuid(@PathVariable UUID uuid) {
        BaseTaxResponse response = baseTaxService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BaseTaxResponse>> getAll(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        List<BaseTaxResponse> responses = baseTaxService.getAll(employee.getManagementEntity());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        baseTaxService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

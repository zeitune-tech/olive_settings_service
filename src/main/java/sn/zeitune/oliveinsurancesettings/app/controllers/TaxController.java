package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxResponse;
import sn.zeitune.oliveinsurancesettings.app.services.TaxService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/taxes")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @PostMapping
    public ResponseEntity<TaxResponse> create(
            @RequestBody @Valid TaxRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        TaxResponse response = taxService.create(request, employee.getManagementEntity());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<TaxResponse> getByUuid(@PathVariable UUID uuid) {
        TaxResponse response = taxService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaxResponse>> getAll(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        List<TaxResponse> responses = taxService.getAll(employee.getManagementEntity());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        taxService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRegimeRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxRegimeResponse;
import sn.zeitune.oliveinsurancesettings.app.services.TaxRegimeService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/tax-regimes")
@RequiredArgsConstructor
public class TaxRegimeController {

    private final TaxRegimeService taxRegimeService;

    @PostMapping
    public ResponseEntity<TaxRegimeResponse> create(
            @RequestBody @Valid TaxRegimeRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        TaxRegimeResponse response = taxRegimeService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<TaxRegimeResponse> getByUuid(@PathVariable UUID uuid) {
        TaxRegimeResponse response = taxRegimeService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaxRegimeResponse>> getAll(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        List<TaxRegimeResponse> responses = taxRegimeService.getAll();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        taxRegimeService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.InsuredRegistryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.InsuredRegistryResponse;
import sn.zeitune.oliveinsurancesettings.app.services.InsuredRegistryService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/insured-registries")
@RequiredArgsConstructor
public class InsuredRegistryController {

    private final InsuredRegistryService insuredRegistryService;

    @PostMapping
    public ResponseEntity<InsuredRegistryResponse> create(
            @RequestBody @Valid InsuredRegistryRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(insuredRegistryService.create(request, employee.getManagementEntity()));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<InsuredRegistryResponse> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(insuredRegistryService.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<Page<InsuredRegistryResponse>> getAll(
            @PageableDefault(size = 10) Pageable pageable,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(insuredRegistryService.getAll(
                employee.getManagementEntity(),
                pageable
        ));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<InsuredRegistryResponse> update(@PathVariable UUID uuid,
                                                          @RequestBody @Valid InsuredRegistryRequest request) {
        return ResponseEntity.ok(insuredRegistryService.update(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        insuredRegistryService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

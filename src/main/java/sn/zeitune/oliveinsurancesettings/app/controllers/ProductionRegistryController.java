package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductionRegistryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductionRegistryResponse;
import sn.zeitune.oliveinsurancesettings.app.services.ProductionRegistryService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/production-registries")
@RequiredArgsConstructor
public class ProductionRegistryController {

    private final ProductionRegistryService productionRegistryService;

    @PostMapping
    public ResponseEntity<ProductionRegistryResponse> create(
            @RequestBody @Valid ProductionRegistryRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(productionRegistryService.create(request, employee.getManagementEntity()));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductionRegistryResponse> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(productionRegistryService.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<Page<ProductionRegistryResponse>> getAll(
            @PageableDefault(size = 10) Pageable pageable,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(productionRegistryService.getAll(
                employee.getManagementEntity(),
                pageable
        ));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ProductionRegistryResponse> update(
            @PathVariable UUID uuid,
         @RequestBody @Valid ProductionRegistryRequest request
    ) {
        return ResponseEntity.ok(productionRegistryService.update(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        productionRegistryService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

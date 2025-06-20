package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.AccessoryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.AccessoryResponse;
import sn.zeitune.oliveinsurancesettings.app.services.AccessoryService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/accessories")
@RequiredArgsConstructor
public class AccessoryController {

    private final AccessoryService accessoryService;

    @PostMapping
    public ResponseEntity<AccessoryResponse> create(
            @RequestBody @Valid AccessoryRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        AccessoryResponse response = accessoryService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<AccessoryResponse> update(
            @PathVariable UUID uuid,
            @RequestBody @Valid AccessoryRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        AccessoryResponse response = accessoryService.update(uuid, request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AccessoryResponse> getByUuid(@PathVariable UUID uuid) {
        AccessoryResponse response = accessoryService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AccessoryResponse>> getAll(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        List<AccessoryResponse> responses = accessoryService.getAll(employee.getManagementEntity());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        accessoryService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ClosureRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ClosureResponse;
import sn.zeitune.oliveinsurancesettings.app.services.ClosureService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/closures")
@RequiredArgsConstructor
public class ClosureController {

    private final ClosureService closureService;

    @PostMapping
    public ResponseEntity<ClosureResponse> create(
            @RequestBody @Valid ClosureRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        ClosureResponse response = closureService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ClosureResponse> getByUuid(@PathVariable UUID uuid) {
        ClosureResponse response = closureService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ClosureResponse>> getAll(
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        List<ClosureResponse> responses = closureService.getAll(
                employee.getManagementEntity()
        );
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ClosureResponse> update(@PathVariable UUID uuid, @RequestBody @Valid ClosureRequest request) {
        ClosureResponse updated = closureService.update(uuid, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        closureService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

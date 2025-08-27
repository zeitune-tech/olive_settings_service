package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.DurationRateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.DurationRateResponse;
import sn.zeitune.oliveinsurancesettings.app.services.DurationRateService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/duration-rates")
@RequiredArgsConstructor
public class DurationRateController {

    private final DurationRateService durationRateService;

    @PostMapping
    public ResponseEntity<DurationRateResponse> create(
            @RequestBody @Valid DurationRateRequest request,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        DurationRateResponse response = durationRateService.create(request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<DurationRateResponse> getByUuid(@PathVariable UUID uuid) {
        DurationRateResponse response = durationRateService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DurationRateResponse>> getAll(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        List<DurationRateResponse> responses = durationRateService.getAllActive(employee.getManagementEntity());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<DurationRateResponse> update(@PathVariable UUID uuid, @RequestBody DurationRateRequest request, Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        DurationRateResponse response = durationRateService.update(uuid, request, employee.getManagementEntity());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        durationRateService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

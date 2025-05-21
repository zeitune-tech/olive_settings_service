package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageResponse;
import sn.zeitune.oliveinsurancesettings.app.services.CoverageService;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/coverages")
@RequiredArgsConstructor
public class CoverageController {

    private final CoverageService coverageService;


    @GetMapping("/{uuid}")
    public ResponseEntity<CoverageResponse> getByUuid(@PathVariable UUID uuid) {
        CoverageResponse response = coverageService.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{productUuid}")
    public ResponseEntity<List<CoverageResponse>> getByProductUuid(
            @PathVariable UUID productUuid,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(coverageService.getByProductUuid(productUuid, employee.getManagementEntity()));
    }

    @GetMapping
    public ResponseEntity<Page<CoverageResponse>> searchCoverages(
            @RequestParam(required = false) String nature,
            @RequestParam(required = false) Boolean isFree,
            @RequestParam(required = false) Boolean isFixed,
            @RequestParam(required = false) CalculationMode calculationMode,
            @RequestParam(required = false) UUID productUuid,
            @RequestParam(required = false) String designation,
            @PageableDefault(size = 10, sort = "nature") Pageable pageable,
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return ResponseEntity.ok(
                coverageService.search(nature, designation, isFree, isFixed, calculationMode, productUuid, employee.getManagementEntity(), pageable)
        );
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CoverageResponse> update(@PathVariable UUID uuid, @RequestBody @Valid CoverageRequest request) {
        CoverageResponse updated = coverageService.update(uuid, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        coverageService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

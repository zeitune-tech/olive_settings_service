package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductCoveragesUpdate;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductUpdate;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponse;
import sn.zeitune.oliveinsurancesettings.app.services.ProductService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductRequestDTO dto,
            Authentication authentication
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Employee employee = (Employee) userDetails;

        return ResponseEntity.ok(productService.createProduct(dto, employee.getManagementEntity()));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable UUID uuid,
            @Valid @RequestBody ProductUpdate dto
    ) {
        return ResponseEntity.ok(productService.updateProduct(uuid, dto));
    }

//    @PutMapping("/{uuid}/coverages")
    public ResponseEntity<ProductResponse> addCoverageToProduct(
            @PathVariable UUID uuid,
            @Valid @RequestBody ProductCoveragesUpdate dto
    ) {
        return ResponseEntity.ok(productService.addCoverageToProduct(uuid, dto));
    }

    @PutMapping("/{uuid}/coverages")
    public ResponseEntity<ProductResponse> updateCoverage(
            @PathVariable UUID uuid,
            @Valid @RequestBody ProductCoveragesUpdate dto
    ) {
        return ResponseEntity.ok(productService.updateCoverage(uuid, dto));
    }

    @PutMapping("/{uuid}/coverages/remove")
    public ResponseEntity<ProductResponse> removeCoverage(
            @PathVariable UUID uuid,
            @Valid @RequestBody ProductCoveragesUpdate dto
    ) {
        return ResponseEntity.ok(productService.removeCoverageFromProduct(uuid, dto));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductResponse> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(productService.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProduct(
            Authentication authentication
    ) {

        Employee employee = (Employee) authentication.getPrincipal();
        List<ProductResponse> result = productService.getByManagementEntityUuid(
                employee.getManagementEntity()
        );
        return ResponseEntity.ok(result);
    }


    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/all-on-branch/{branchUuid}")
    public ResponseEntity<List<ProductResponse>> getAllOnBranch(
            @PathVariable UUID branchUuid,
            Authentication authentication
        ) {
        return ResponseEntity.ok(
                productService.getAllOnBranch(
                    ((Employee) authentication.getPrincipal()).getManagementEntity(),
                    branchUuid
                ));
    }

    @PatchMapping("/{uuid}/share")
    public ResponseEntity<Void> sharePublicProduct(
            @PathVariable UUID uuid,
            @RequestBody List<UUID> companyUuids
    ) {
        productService.sharePublicProductWithCompanies(uuid, companyUuids);
        return ResponseEntity.noContent().build();
    }
}

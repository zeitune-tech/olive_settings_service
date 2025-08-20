package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.*;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleReferentialService;

import java.util.UUID;

@RestController
@RequestMapping("/app/vehicle-referential")
@RequiredArgsConstructor
public class VehicleReferentialController {

    private final VehicleReferentialService vehicleReferentialService;

    /**
     * Liste toutes les marques et leurs modèles (optionnellement filtrées par le nom de la marque).
     */
    @GetMapping("")
    public ResponseEntity<Page<VehicleBrandWithModelsResponseDTO>> getAll(
            Pageable pageable,
            @RequestParam(required = false) String brandName
    ) {
        if (brandName != null && !brandName.isBlank()) {
            return ResponseEntity.ok(vehicleReferentialService.findAllWithName(pageable, brandName));
        }
        return ResponseEntity.ok(vehicleReferentialService.getAll(pageable));
    }

    /**
     * Liste toutes les marques (optionnellement filtrées par nom).
     */
    @GetMapping("/brands")
    public ResponseEntity<Page<VehicleBrandResponseDTO>> getBrands(
            Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(vehicleReferentialService.findBrandsWithName(pageable, name));
        }
        return ResponseEntity.ok(vehicleReferentialService.getAllBrands(pageable));
    }

    /**
     * Liste tous les modèles d'une marque (optionnellement filtrés par nom).
     */
    @GetMapping("/brands/{uuid}/models")
    public ResponseEntity<Page<VehicleModelResponseDTO>> getModelsOfBrand(
            @PathVariable UUID uuid,
            Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(vehicleReferentialService.findModelsWithName(pageable, uuid, name));
        }
        return ResponseEntity.ok(vehicleReferentialService.getModelsByBrand(pageable, uuid));
    }


    /**
     * Liste tous les modèles (optionnellement filtrés par nom).
     */
    @GetMapping("/models")
    public ResponseEntity<Page<VehicleModelResponseDTO>> getModels(
            Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(vehicleReferentialService.findModelsWithName(pageable, name));
        }
        return ResponseEntity.ok(vehicleReferentialService.getAllModels(pageable));
    }

}
package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleBrandRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleModelRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.*;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Brand;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Model;
import sn.zeitune.oliveinsurancesettings.app.mappers.VehicleBrandMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.VehicleModelMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.VehicleBrandRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.VehicleModelRepository;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleReferentialService;
import sn.zeitune.oliveinsurancesettings.enums.BodyWorkType;
import sn.zeitune.oliveinsurancesettings.enums.MotorizationType;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
}
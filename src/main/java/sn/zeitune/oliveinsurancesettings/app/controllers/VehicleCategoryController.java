package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleCategoryRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleCategoryUpdateRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleCategoryResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleCategoryService;

import java.util.UUID;

@RestController
@RequestMapping("/app/vehicle-categories")
@RequiredArgsConstructor
public class VehicleCategoryController {

    private final VehicleCategoryService vehicleUsageService;

    @GetMapping("")
    public  ResponseEntity<Page<VehicleCategoryResponseDTO>> getAllVehicleCategories(Pageable pageable) {
        Page<VehicleCategoryResponseDTO> vehicleUsages = vehicleUsageService.getVehicleCategoriesListResponseDTO(pageable);
        return ResponseEntity.ok(vehicleUsages);
    }

    @PostMapping("")
    public ResponseEntity<VehicleCategoryResponseDTO> createVehicleCategory(@RequestBody VehicleCategoryRequestDTO requestDTO) {
        VehicleCategoryResponseDTO vehicleUsageResponseDTO = vehicleUsageService.createVehicleCategory(requestDTO);
        return ResponseEntity.ok(vehicleUsageResponseDTO);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<VehicleCategoryResponseDTO> updateVehicleCategory(
            @PathVariable UUID uuid,
            @RequestBody VehicleCategoryUpdateRequestDTO requestDTO) {
        VehicleCategoryResponseDTO vehicleUsageResponseDTO = vehicleUsageService.updateVehicleCategory(uuid, requestDTO);
        return ResponseEntity.ok(vehicleUsageResponseDTO);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteVehicleCategory(@PathVariable UUID uuid) {
        vehicleUsageService.deleteVehicleCategory(uuid);
        return ResponseEntity.noContent().build();
    }

}
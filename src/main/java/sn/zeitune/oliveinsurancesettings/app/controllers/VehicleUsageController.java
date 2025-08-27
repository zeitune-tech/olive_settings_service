package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleUsageRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandWithModelsResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleModelResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleUsageResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleReferentialService;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleUsageService;

import java.util.UUID;

@RestController
@RequestMapping("/app/vehicle-usages")
@RequiredArgsConstructor
public class VehicleUsageController {

    private final VehicleUsageService vehicleUsageService;

    @GetMapping("")
    public  ResponseEntity<Page<VehicleUsageResponseDTO>> getAllVehicleUsages(Pageable pageable) {
        Page<VehicleUsageResponseDTO> vehicleUsages = vehicleUsageService.getVehicleUsagesListResponseDTO(pageable);
        return ResponseEntity.ok(vehicleUsages);
    }

    @PostMapping("")
    public ResponseEntity<VehicleUsageResponseDTO> createVehicleUsage(@RequestBody VehicleUsageRequestDTO requestDTO) {
        VehicleUsageResponseDTO vehicleUsageResponseDTO = vehicleUsageService.createVehicleUsage(requestDTO);
        return ResponseEntity.ok(vehicleUsageResponseDTO);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<VehicleUsageResponseDTO> updateVehicleUsage(
            @PathVariable UUID uuid,
            @RequestBody VehicleUsageRequestDTO requestDTO) {
        VehicleUsageResponseDTO vehicleUsageResponseDTO = vehicleUsageService.updateVehicleUsage(uuid, requestDTO);
        return ResponseEntity.ok(vehicleUsageResponseDTO);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteVehicleUsage(@PathVariable UUID uuid) {
        vehicleUsageService.deleteVehicleUsage(uuid);
        return ResponseEntity.noContent().build();
    }

}
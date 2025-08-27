package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleDTTReferentialResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleModelResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleDTTReferentialService;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleReferentialService;

import java.util.UUID;

@RestController
@RequestMapping("/app/vehicle-dtt-referential")
@RequiredArgsConstructor
public class VehicleDTTReferentialController {

    private final VehicleDTTReferentialService vehicleDTTReferentialService;

    @GetMapping
    public ResponseEntity<Page<VehicleDTTReferentialResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(vehicleDTTReferentialService.getAll(pageable));
    }

    @GetMapping("/{registrationNumber}")
    public ResponseEntity<VehicleDTTReferentialResponseDTO> getByRegistrationNumber(@PathVariable String registrationNumber) {
        return ResponseEntity.ok(vehicleDTTReferentialService.findByRegistrationNumber(registrationNumber));
    }
}


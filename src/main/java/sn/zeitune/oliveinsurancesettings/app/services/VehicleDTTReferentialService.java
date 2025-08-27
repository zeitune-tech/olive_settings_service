package sn.zeitune.oliveinsurancesettings.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleDTTReferentialRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleDTTReferentialSimpleRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleDTTReferentialResponseDTO;

import java.util.List;
import java.util.UUID;

public interface VehicleDTTReferentialService {

    VehicleDTTReferentialResponseDTO create(VehicleDTTReferentialRequestDTO dto);
    VehicleDTTReferentialResponseDTO create(VehicleDTTReferentialSimpleRequestDTO dto);

    VehicleDTTReferentialResponseDTO update(UUID uuid, VehicleDTTReferentialRequestDTO dto);

    Page<VehicleDTTReferentialResponseDTO> getAll(Pageable pageable);
    VehicleDTTReferentialResponseDTO findByRegistrationNumber(String registrationNumber);
}

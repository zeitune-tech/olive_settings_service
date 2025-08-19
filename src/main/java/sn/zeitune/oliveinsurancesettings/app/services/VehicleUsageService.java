package sn.zeitune.oliveinsurancesettings.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleUsageRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleUsageResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.VehicleUsage;

import java.util.UUID;

public interface VehicleUsageService {
    /**
     * Creates a new vehicle usage.
     *
     * @param requestDTO the vehicle usage
     * @return the created vehicle usage response DTO
     */
    VehicleUsageResponseDTO createVehicleUsage(VehicleUsageRequestDTO requestDTO);


    /**
     * Updates an existing vehicle usage.
     *
     * @param uuid       the UUID of the vehicle usage to update
     * @param requestDTO the new vehicle usage
     * @return the updated vehicle usage response DTO
     */
    VehicleUsageResponseDTO updateVehicleUsage(UUID uuid, VehicleUsageRequestDTO requestDTO);

    /**
     * Deletes a vehicle usage by its UUID.
     *
     * @param uuid the UUID of the vehicle usage to delete
     */
    void deleteVehicleUsage(UUID uuid);

    /**
     * Récupère une page de véhicules utilisations.
     *
     * @param pageable les informations de pagination
     * @return une page contenant les DTO de réponse des utilisations de véhicules
     */
    Page<VehicleUsageResponseDTO> getVehicleUsagesListResponseDTO(Pageable pageable);

    VehicleUsage getEntityByUuid(UUID uuid);
}

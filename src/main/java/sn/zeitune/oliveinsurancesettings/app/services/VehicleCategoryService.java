package sn.zeitune.oliveinsurancesettings.app.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleCategoryUpdateRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleCategoryResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.VehicleCategory;

import java.util.UUID;

public interface VehicleCategoryService {

    /**
     * Creates a new vehicle category.
     *
     * @param requestDTO the vehicle category request DTO
     * @return the created vehicle category
     */
    VehicleCategoryResponseDTO createVehicleCategory(VehicleCategoryUpdateRequestDTO requestDTO);

    /**
     * Updates an existing vehicle category.
     *
     * @param uuid       the UUID of the vehicle category to update
     * @param updateRequestDTO the new vehicle category request DTO
     * @return the updated vehicle category
     */
    VehicleCategoryResponseDTO updateVehicleCategory(UUID uuid, VehicleCategoryUpdateRequestDTO updateRequestDTO);

    /**
     * Deletes a vehicle category by its UUID.
     *
     * @param uuid the UUID of the vehicle category to delete
     */
    void deleteVehicleCategory(UUID uuid);

    /**
     * Retrieves a page of vehicle categories.
     *
     * @param pageable the pagination information
     * @return a page containing the vehicle categories
     */
    Page<VehicleCategoryResponseDTO> getVehicleCategoriesListResponseDTO(Pageable pageable);


    VehicleCategory getEntityByUuid(UUID uuid);
}

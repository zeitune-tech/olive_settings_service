package sn.zeitune.oliveinsurancesettings.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleBrandRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleModelRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandWithModelsResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleModelResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Brand;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Model;

import java.util.List;
import java.util.UUID;

public interface VehicleReferentialService {

    VehicleBrandResponseDTO createBrand(String brandName);

    VehicleBrandResponseDTO addNewModelToBrand(UUID brandUuid, VehicleModelRequestDTO vehicleModel);

    VehicleBrandResponseDTO updateBrand(UUID uuid, VehicleBrandRequestDTO dto);

    VehicleModelResponseDTO updateModel(UUID uuid, VehicleModelRequestDTO dto);

    void deleteBrand(UUID uuid);

    void deleteModel(UUID uuid);

    VehicleBrandResponseDTO getBrandByUuid(UUID uuid);
    VehicleModelResponseDTO getModelByUuid(UUID uuid);

    Page<VehicleBrandWithModelsResponseDTO> getAll(Pageable pageable);
    Page<VehicleBrandWithModelsResponseDTO> findAllWithName(Pageable pageable, String name);

    Page<VehicleBrandResponseDTO> getAllBrands(Pageable pageable);
    Page<VehicleBrandResponseDTO> findBrandsWithName(Pageable pageable, String name);


    Page<VehicleModelResponseDTO> getModelsByBrand(Pageable pageable, UUID brandUuid);
    Page<VehicleModelResponseDTO> findModelsWithName(Pageable pageable, UUID brandUuid, String name);



    Brand getBrandIfExist (UUID uuid);

    Model getModelIfExistOnBrand (Brand brand, UUID uuid);

    Brand findBrandEntityByName(String name);

    Model findModelEntityByBrandAndName(Brand brand, String modelName);

}

package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleBrandRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleModelRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandWithModelsResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleModelResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Brand;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Model;
import sn.zeitune.oliveinsurancesettings.app.mappers.VehicleBrandMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.VehicleBrandWithModelsMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.VehicleModelMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.VehicleBrandRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.VehicleModelRepository;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleReferentialService;
import sn.zeitune.oliveinsurancesettings.enums.BodyWorkType;
import sn.zeitune.oliveinsurancesettings.enums.MotorizationType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleReferentialServiceImpl implements VehicleReferentialService {

    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleBrandRepository vehicleBrandRepository;

    @Override
    public VehicleBrandResponseDTO createBrand(String brandName) {
        if (vehicleBrandRepository.existsByName(brandName.toUpperCase())) {
            throw new IllegalArgumentException("Brand already exists with name: " + brandName.toUpperCase());
        }

        Brand brand = vehicleBrandRepository.save(
                Brand.builder()
                        .name(brandName.toUpperCase())
                        .build()
        );

        return VehicleBrandMapper.map(brand);
    }

    @Override
    public VehicleBrandResponseDTO addNewModelToBrand(UUID brandUuid, VehicleModelRequestDTO vehicleModel) {
        Brand brand = getBrandIfExist(brandUuid);
        Model vehicleModelSaving = VehicleModelMapper.map(vehicleModel);
        vehicleModelSaving.setBrand(brand);
        vehicleModelRepository.save(vehicleModelSaving);
        return VehicleBrandMapper.map(
                brand
        );
    }

    @Override
    public VehicleBrandResponseDTO updateBrand(UUID brandUuid, VehicleBrandRequestDTO dto) {
        Brand brand = getBrandIfExist(brandUuid);
        brand.setName(dto.name());

        // Récupérer les modèles existants
        List<Model> currentModels = getModelsByBrand(brand);
        Set<UUID> incomingModelIds = new HashSet<>(dto.modelIds());

        // Supprimer les modèles qui ne sont plus dans la liste
        for (Model m : currentModels) {
            if (!incomingModelIds.contains(m.getUuid())) {
                m.setBrand(null);
                vehicleModelRepository.save(m);
            }
        }

        // Ajouter les nouveaux modèles
        for (UUID modelId : incomingModelIds) {
            Model m = getModelIfExistOnBrand(brand, modelId);
            if (!currentModels.contains(m)) {
                m.setBrand(brand);
                vehicleModelRepository.save(m);
            }
        }

        return VehicleBrandMapper.map(brand);
    }

    @Override
    public VehicleModelResponseDTO updateModel(UUID uuid, VehicleModelRequestDTO dto) {
        Model model = getModelIfExist(uuid);
        model.setName(dto.name().toUpperCase());
        model.setMotorizationType(
                dto.motorizationType() != null
                        ? MotorizationType.fromLabel(dto.motorizationType())
                        : null
        );
        model.setBodywork(
                dto.bodywork() != null
                        ? BodyWorkType.fromLabel(dto.bodywork())
                        : null
        );
        model.setPlaceCount(dto.placeCount());
        model.setHasTurbo(dto.hasTurbo());
        model.setHorsepower(dto.horsepower());
        model.setDisplacement(dto.displacement());
        model.setWeight(dto.weight());
        model.setNature(dto.nature());

        return VehicleModelMapper.map(
                vehicleModelRepository.save(model)
        );
    }

    @Override
    public void deleteBrand(UUID uuid) {
        Brand brand = getBrandIfExist(uuid);
        vehicleBrandRepository.delete(brand);
    }

    @Override
    public void deleteModel(UUID uuid) {
        Model model = getModelIfExist(uuid);
        // Remove the model from its brand
        if (model.getBrand() != null) {
            model.setBrand(null);
            vehicleModelRepository.save(model);
        }
        vehicleModelRepository.delete(model);

    }

    @Override
    public VehicleBrandResponseDTO getBrandByUuid(UUID uuid) {
        Brand brand = getBrandIfExist(uuid);
        return VehicleBrandMapper.map(
                brand
        );
    }

    @Override
    public VehicleModelResponseDTO getModelByUuid(UUID uuid) {
        Model model = getModelIfExist(uuid);
        return VehicleModelMapper.map(
                model
        );
    }

    @Override
    public Page<VehicleBrandWithModelsResponseDTO> getAll(Pageable pageable) {
        return vehicleBrandRepository.findAll(pageable).map(
                brand -> VehicleBrandWithModelsMapper.map(brand, getModelsByBrand(brand))
        );
    }

    @Override
    public Page<VehicleBrandWithModelsResponseDTO> findAllWithName(Pageable pageable, String name) {
        return vehicleBrandRepository.findByNameContainingIgnoreCase(name, pageable).map(
                brand -> VehicleBrandWithModelsMapper.map(brand, getModelsByBrand(brand))
        );
    }

    @Override
    public Page<VehicleBrandResponseDTO> getAllBrands(Pageable pageable) {
        return vehicleBrandRepository.findAll(pageable).map(
                VehicleBrandMapper::map
        );
    }

    @Override
    public Page<VehicleBrandResponseDTO> findBrandsWithName(Pageable pageable, String name) {
        return vehicleBrandRepository.findByNameContainingIgnoreCase(name, pageable).map(VehicleBrandMapper::map);
    }

    @Override
    public Page<VehicleModelResponseDTO> getModelsByBrand(Pageable pageable, UUID brandUuid) {
        Brand brand = getBrandIfExist(brandUuid);
        return vehicleModelRepository.findAllByBrand(pageable, brand).map(
                VehicleModelMapper::map
        );
    }

    @Override
    public Page<VehicleModelResponseDTO> findModelsWithName(Pageable pageable, UUID brandUuid, String name) {
        return vehicleModelRepository.findByNameContainingIgnoreCaseAndBrand(name, getBrandIfExist(brandUuid), pageable)
                .map(VehicleModelMapper::map);
    }

    @Override
    public Brand findBrandEntityByName(String name) {
        return vehicleBrandRepository.findByName(name.toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with name: " + name));
    }

    @Override
    public Model findModelEntityByBrandAndName(Brand brand, String modelName) {
        return vehicleModelRepository.findByBrandAndName(brand, modelName.toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Model not found with name: " + modelName + " for brand: " + brand.getName()));
    }

    @Override
    public Brand getBrandIfExist (UUID uuid) {
        return vehicleBrandRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with UUID: " + uuid));
    }

    @Override
    public Model getModelIfExistOnBrand (Brand brand, UUID uuid) {
        return vehicleModelRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Model not found with UUID: " + uuid));

    }

    private List<Model> getModelsByBrand(Brand brand) {
        return vehicleModelRepository.findAllByBrand(brand).stream().toList();
    }

    private void addModelToBrand(Brand brand, Model model) {
        model.setBrand(brand);
        vehicleModelRepository.save(model);
    }

    private Model getModelIfExist(UUID uuid) {
        return vehicleModelRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Model not found with UUID: " + uuid));
    }
}


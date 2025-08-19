package sn.zeitune.oliveinsurancesettings.app.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleCategoryRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleCategoryUpdateRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleCategoryResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.VehicleCategory;
import sn.zeitune.oliveinsurancesettings.app.entities.VehicleUsage;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Model;
import sn.zeitune.oliveinsurancesettings.app.mappers.VehicleCategoryMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.VehicleCategoryRepository;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleCategoryService;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleCategoryService;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleUsageService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleCategoryServiceImpl implements VehicleCategoryService {

    private final VehicleCategoryRepository vehicleCategoryRepository;
    private final VehicleUsageService vehicleUsageService;
    private final ProductRepository productRepository;

    @Override
    public VehicleCategoryResponseDTO createVehicleCategory(VehicleCategoryRequestDTO requestDTO) {
        if (vehicleCategoryRepository.existsByNameIgnoreCase(requestDTO.name()))
            throw new RuntimeException("Vehicle usage with name " + requestDTO.name() + " already exists.");

        VehicleCategory vehicleCategory = new VehicleCategory();
        VehicleCategoryMapper.put(vehicleCategory, requestDTO);

        return VehicleCategoryMapper.map(vehicleCategoryRepository.save(vehicleCategory));
    }

    @Override
    public VehicleCategoryResponseDTO updateVehicleCategory(UUID uuid, VehicleCategoryUpdateRequestDTO requestDTO) {
        VehicleCategory vehicleCategory = getEntityByUuid(uuid);
        if (!vehicleCategory.getName().equals(requestDTO.name()) &&
                vehicleCategoryRepository.existsByNameIgnoreCase(requestDTO.name()))
            throw new RuntimeException("Vehicle category with name " + requestDTO.name() + " already exists.");

        VehicleCategoryMapper.put(vehicleCategory, requestDTO);

        // Supprimer les usages qui ne sont plus dans la liste
        Set<UUID> incomingVehicleUsageIds = requestDTO.usages();
        for (VehicleUsage u : vehicleCategory.getUsages()) {
            if (!incomingVehicleUsageIds.contains(u.getUuid())) {
                u.getCategories().remove(vehicleCategory);
                vehicleCategory.getUsages().remove(u);
            }
        }

        // Ajouter les nouveaux usages
        for (UUID usageUuid : requestDTO.usages()) {
            VehicleUsage usageEntity = vehicleUsageService.getEntityByUuid(usageUuid);
            if (!vehicleCategory.getUsages().contains(usageEntity)) {
                vehicleCategory.getUsages().add(usageEntity);
            }
        }

        // Supprimer les produits qui ne sont plus dans la liste
        Set<UUID> productIds = requestDTO.products();
        for (Product p : vehicleCategory.getProducts()) {
            if (!productIds.contains(p.getUuid())) {
                vehicleCategory.getProducts().remove(p);
            }
        }

        // Ajouter les nouveaux produits
        for (UUID productUuid : requestDTO.products()) {
            Product productEntity = getProductIfExist(productUuid);
            if (!vehicleCategory.getProducts().contains(productEntity)) {
                vehicleCategory.getProducts().add(productEntity);
            }
        }

        return VehicleCategoryMapper.map(vehicleCategoryRepository.save(vehicleCategory));
    }

    @Override
    public void deleteVehicleCategory(UUID uuid) {
        VehicleCategory vehicleCategory = getEntityByUuid(uuid);
        vehicleCategoryRepository.delete(vehicleCategory);
    }

    @Override
    public Page<VehicleCategoryResponseDTO> getVehicleCategoriesListResponseDTO(Pageable pageable) {
        return vehicleCategoryRepository.findAll(pageable).map( VehicleCategoryMapper::map);
    }

    @Override
    public VehicleCategory getEntityByUuid(UUID uuid) {
        return vehicleCategoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Vehicle usage not found with id: " + uuid));
    }

    private Product getProductIfExist(UUID productUuid) {
        return productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productUuid));
    }
}
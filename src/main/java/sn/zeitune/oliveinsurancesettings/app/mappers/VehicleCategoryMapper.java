package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleCategoryUpdateRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleCategoryResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.VehicleCategory;

import java.util.stream.Collectors;

public class VehicleCategoryMapper {

    public static void put(VehicleCategory vehicleCategory, VehicleCategoryUpdateRequestDTO vehicleCategoryRequestDTO) {
        if (vehicleCategoryRequestDTO == null || vehicleCategory == null) return;
        vehicleCategory.setName(vehicleCategoryRequestDTO.name().toUpperCase());
        vehicleCategory.setWithTrailer(vehicleCategoryRequestDTO.withTrailer());
        vehicleCategory.setWithChassis(vehicleCategoryRequestDTO.withChassis());
    }

    public static VehicleCategoryResponseDTO map(VehicleCategory vehicleCategory) {
        if (vehicleCategory == null) return null;

        return VehicleCategoryResponseDTO.builder()
                .id(vehicleCategory.getUuid())
                .name(vehicleCategory.getName())
                .withTrailer(vehicleCategory.getWithTrailer())
                .withChassis(vehicleCategory.getWithChassis())
                .products(vehicleCategory.getProducts() != null ?
                        vehicleCategory.getProducts().stream().map(ProductMapper::map).collect(Collectors.toSet()) : null)
                .usages(vehicleCategory.getUsages() != null ?
                        vehicleCategory.getUsages().stream().map(VehicleUsageMapper::map).collect(Collectors.toSet()) : null)
                .build();
    }
}

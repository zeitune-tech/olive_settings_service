package sn.zeitune.oliveinsurancesettings.app.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleDTTReferentialRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleDTTReferentialSimpleRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleDTTReferentialResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Brand;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.DTTReferential;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Model;
import sn.zeitune.oliveinsurancesettings.app.mappers.VehicleDTTReferentialMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.VehicleDTTReferentialRepository;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleDTTReferentialService;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleReferentialService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleDTTReferentialServiceImpl implements VehicleDTTReferentialService {

    private final VehicleDTTReferentialRepository vehicleDTTReferentialRepository;
    private final VehicleReferentialService vehicleReferentialService;

    @Override
    public VehicleDTTReferentialResponseDTO create(VehicleDTTReferentialRequestDTO dto) {
        Brand brand = vehicleReferentialService.getBrandIfExist(dto.brandId());
        Model model = vehicleReferentialService.getModelIfExistOnBrand(brand, dto.modelId());
        return VehicleDTTReferentialMapper.map(
                vehicleDTTReferentialRepository.save(
                        DTTReferential.builder()
                                .registrationNumber(dto.registrationNumber().toUpperCase())
                                .model(model)
                                .build()
                )
        );
    }

    @Override
    public VehicleDTTReferentialResponseDTO create(VehicleDTTReferentialSimpleRequestDTO dto) {
        Brand brand = vehicleReferentialService.findBrandEntityByName(dto.brandName());
        Model model = vehicleReferentialService.findModelEntityByBrandAndName(brand, dto.modelName());
        return VehicleDTTReferentialMapper.map(
                vehicleDTTReferentialRepository.save(
                        DTTReferential.builder()
                        .registrationNumber(dto.registrationNumber().toUpperCase())
                        .model(model)
                        .build()
                )
        );
    }

    @Override
    public VehicleDTTReferentialResponseDTO update(UUID uuid, VehicleDTTReferentialRequestDTO dto) {
        return null;
    }

    @Override
    public Page<VehicleDTTReferentialResponseDTO> getAll(Pageable pageable) {
        return vehicleDTTReferentialRepository.findAll(pageable)
                .map(VehicleDTTReferentialMapper::map);
    }

    @Override
    public VehicleDTTReferentialResponseDTO findByRegistrationNumber(String registrationNumber) {
        return VehicleDTTReferentialMapper.map(
                vehicleDTTReferentialRepository.findByRegistrationNumber(registrationNumber.toUpperCase())
                        .orElse(null)
        );
    }
}

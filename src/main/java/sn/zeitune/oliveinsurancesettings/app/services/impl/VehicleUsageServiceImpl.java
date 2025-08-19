package sn.zeitune.oliveinsurancesettings.app.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleUsageRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleUsageResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.VehicleUsage;
import sn.zeitune.oliveinsurancesettings.app.mappers.VehicleUsageMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.VehicleUsageRepository;
import sn.zeitune.oliveinsurancesettings.app.services.VehicleUsageService;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleUsageServiceImpl implements VehicleUsageService {

    private final VehicleUsageRepository vehicleUsageRepository;

    @Override
    public VehicleUsageResponseDTO createVehicleUsage(VehicleUsageRequestDTO requestDTO) {
        if (vehicleUsageRepository.existsByName(requestDTO.name()))
            throw new RuntimeException("Vehicle usage with name " + requestDTO.name() + " already exists.");

        VehicleUsage vehicleUsage = new VehicleUsage();
        VehicleUsageMapper.put(vehicleUsage, requestDTO);

        return VehicleUsageMapper.map(vehicleUsageRepository.save(vehicleUsage));
    }

    @Override
    public VehicleUsageResponseDTO updateVehicleUsage(UUID uuid, VehicleUsageRequestDTO requestDTO) {
        VehicleUsage vehicleUsage = getEntityByUuid(uuid);
        if (!vehicleUsage.getName().equals(requestDTO.name()) &&
                vehicleUsageRepository.existsByName(requestDTO.name()))
            throw new RuntimeException("Vehicle usage with name " + requestDTO.name() + " already exists.");

        VehicleUsageMapper.put(vehicleUsage, requestDTO);
        return VehicleUsageMapper.map(vehicleUsageRepository.save(vehicleUsage));
    }

    @Override
    public void deleteVehicleUsage(UUID uuid) {
        VehicleUsage vehicleUsage = getEntityByUuid(uuid);
        vehicleUsageRepository.delete(vehicleUsage);
    }

    @Override
    public Page<VehicleUsageResponseDTO> getVehicleUsagesListResponseDTO(Pageable pageable) {
        return vehicleUsageRepository.findAll(pageable).map( VehicleUsageMapper::map);
    }

    @Override
    public VehicleUsage getEntityByUuid(UUID uuid) {
        return vehicleUsageRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Vehicle usage not found with id: " + uuid));
    }
}
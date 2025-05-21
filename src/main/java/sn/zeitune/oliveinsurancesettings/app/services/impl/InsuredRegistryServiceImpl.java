package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.InsuredRegistryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.InsuredRegistryResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.InsuredRegistry;
import sn.zeitune.oliveinsurancesettings.app.mappers.InsuredRegistryMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.InsuredRegistryRepository;
import sn.zeitune.oliveinsurancesettings.app.services.InsuredRegistryService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InsuredRegistryServiceImpl implements InsuredRegistryService {

    private final InsuredRegistryRepository insuredRegistryRepository;

    @Override
    public InsuredRegistryResponse create(InsuredRegistryRequest request, UUID managementEntity) {
        InsuredRegistry entity = InsuredRegistryMapper.map(request);
        entity.setManagementEntity(managementEntity);
        InsuredRegistry saved = insuredRegistryRepository.save(entity);
        return InsuredRegistryMapper.map(saved);
    }

    @Override
    public InsuredRegistryResponse getByUuid(UUID uuid) {
        InsuredRegistry entity = insuredRegistryRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("InsuredRegistry not found with UUID: " + uuid));
        return InsuredRegistryMapper.map(entity);
    }

    @Override
    public Page<InsuredRegistryResponse> getAll(UUID managementEntity, Pageable pageable) {
        return insuredRegistryRepository.findAllByManagementEntity(managementEntity, pageable)
                .map(InsuredRegistryMapper::map);
    }

    @Override
    public InsuredRegistryResponse update(UUID uuid, InsuredRegistryRequest request) {
        InsuredRegistry existing = insuredRegistryRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("InsuredRegistry not found with UUID: " + uuid));

        existing.setPrefix(request.prefix());
        existing.setLength(request.length());

        return InsuredRegistryMapper.map(insuredRegistryRepository.save(existing));
    }

    @Override
    public void delete(UUID uuid) {
        InsuredRegistry entity = insuredRegistryRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("InsuredRegistry not found with UUID: " + uuid));
        insuredRegistryRepository.delete(entity);
    }
}

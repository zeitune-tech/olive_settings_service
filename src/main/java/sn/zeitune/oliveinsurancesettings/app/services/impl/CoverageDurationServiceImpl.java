package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageDurationRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageDurationResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.CoverageDuration;
import sn.zeitune.oliveinsurancesettings.app.mappers.CoverageDurationMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageDurationRepository;
import sn.zeitune.oliveinsurancesettings.app.services.CoverageDurationService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CoverageDurationServiceImpl implements CoverageDurationService {

    private final CoverageDurationRepository coverageDurationRepository;

    @Override
    public CoverageDurationResponse create(CoverageDurationRequest request, UUID managementEntity) {
        CoverageDuration entity = CoverageDurationMapper.map(request);
        entity.setManagementEntity(managementEntity);
        CoverageDuration saved = coverageDurationRepository.save(entity);
        return CoverageDurationMapper.map(saved);
    }

    @Override
    public CoverageDurationResponse getByUuid(UUID uuid) {
        CoverageDuration duration = coverageDurationRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CoverageDuration not found with UUID: " + uuid));
        return CoverageDurationMapper.map(duration);
    }

    @Override
    public List<CoverageDurationResponse> getAll(UUID managementEntity) {
        return coverageDurationRepository.findAllByManagementEntity(managementEntity).stream()
                .map(CoverageDurationMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public CoverageDurationResponse update(UUID uuid, CoverageDurationRequest request) {
        CoverageDuration existing = coverageDurationRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CoverageDuration not found with UUID: " + uuid));

        existing.setFrom(request.from());
        existing.setTo(request.to());
        existing.setType(request.type());
        existing.setProrotaMode(request.prorotaMode());
        existing.setUnit(request.unit());

        return CoverageDurationMapper.map(coverageDurationRepository.save(existing));
    }

    @Override
    public void delete(UUID uuid) {
        CoverageDuration duration = coverageDurationRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CoverageDuration not found with UUID: " + uuid));
        coverageDurationRepository.delete(duration);
    }
}

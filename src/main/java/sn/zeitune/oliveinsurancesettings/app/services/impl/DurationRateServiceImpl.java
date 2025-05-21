package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.DurationRateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.DurationRateResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.DurationRate;
import sn.zeitune.oliveinsurancesettings.app.mappers.DurationRateMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.DurationRateRepository;
import sn.zeitune.oliveinsurancesettings.app.services.DurationRateService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DurationRateServiceImpl implements DurationRateService {

    private final DurationRateRepository repository;

    @Override
    public DurationRateResponse create(DurationRateRequest request, UUID managementEntity) {
        DurationRate entity = DurationRateMapper.map(request);
        entity.setManagementEntity(managementEntity);
        entity = repository.save(entity);
        return DurationRateMapper.map(entity);
    }


    @Override
    public DurationRateResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(DurationRateMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("DurationRate not found"));
    }

    @Override
    public List<DurationRateResponse> getAll() {
        return repository.findAll().stream()
                .map(DurationRateMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        DurationRate entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("DurationRate not found"));
        repository.delete(entity);
    }
}

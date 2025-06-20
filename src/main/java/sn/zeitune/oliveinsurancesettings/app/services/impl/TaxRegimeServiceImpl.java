package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRegimeRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxRegimeResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Tax;
import sn.zeitune.oliveinsurancesettings.app.entities.TaxRegime;
import sn.zeitune.oliveinsurancesettings.app.mappers.TaxRegimeMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxRegimeRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxRepository;
import sn.zeitune.oliveinsurancesettings.app.services.TaxRegimeService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaxRegimeServiceImpl implements TaxRegimeService {

    private final TaxRegimeRepository repository;
    private final TaxRepository taxRepository;

    @Override
    public TaxRegimeResponse create(TaxRegimeRequest request, UUID managementEntity) {
        Set<Tax> taxes = request.exemptedTaxIds().stream()
                .map(id -> taxRepository.findByUuid(id)
                        .orElseThrow(() -> new IllegalArgumentException("Tax not found: " + id)))
                .collect(Collectors.toSet());

        TaxRegime regime = TaxRegimeMapper.map(request, taxes);
        regime.setManagementEntity(managementEntity);
        regime = repository.save(regime);
        return TaxRegimeMapper.map(regime);
    }


    @Override
    public TaxRegimeResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(TaxRegimeMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("TaxRegime not found"));
    }

    @Override
    public List<TaxRegimeResponse> getAll() {
        return repository.findAll().stream()
                .map(TaxRegimeMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        TaxRegime regime = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("TaxRegime not found"));

        regime.setDeleted(true);
        repository.save(regime);
    }
}

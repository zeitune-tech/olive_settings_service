package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxTypeRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxTypeResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxType;
import sn.zeitune.oliveinsurancesettings.app.mappers.TaxMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxTypeRepository;
import sn.zeitune.oliveinsurancesettings.app.services.TaxTypeService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TaxTypeServiceImpl implements TaxTypeService {

    private final TaxTypeRepository taxTypeRepository;

    @Override
    public TaxTypeResponse create(TaxTypeRequest request, UUID managementEntity) {

        if (taxTypeRepository.existsByNameAndDeletedFalse(request.name())) {
            throw new IllegalArgumentException("TaxType with this name already exists");
        }

        TaxType taxType = TaxMapper.map(request);
        taxType.setManagementEntity(managementEntity);
        taxTypeRepository.save(taxType);
        return TaxMapper.map(taxType);
    }

    @Override
    public TaxTypeResponse update(UUID uuid, TaxTypeRequest request) {
        TaxType taxType = taxTypeRepository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new IllegalArgumentException("TaxType not found"));

        if (!request.name().equals(taxType.getName()) &&
            taxTypeRepository.existsByNameAndDeletedFalse(request.name())) {
            throw new IllegalArgumentException("TaxType with this name already exists");
        }

        taxType.setName(request.name());
        taxType.setNature(request.nature());

        taxTypeRepository.save(taxType);
        return TaxMapper.map(taxType);
    }

    @Override
    public TaxTypeResponse getByUuid(UUID uuid) {
        TaxType taxType = taxTypeRepository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new IllegalArgumentException("TaxType not found"));
        return TaxMapper.map(taxType);
    }

    @Override
    public List<TaxTypeResponse> getAll() {
        List<TaxType> taxTypes = taxTypeRepository.findAllByDeletedFalse();
        return taxTypes.stream()
                .map(TaxMapper::map)
                .toList();
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        TaxType taxType = taxTypeRepository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new IllegalArgumentException("TaxType not found"));
        taxType.setDeleted(true);
        taxTypeRepository.save(taxType);
    }

    @Override
    public List<TaxTypeResponse> getAllByManagementEntity(UUID managementEntity) {
        List<TaxType> taxTypes = taxTypeRepository.findAllByManagementEntityAndDeletedFalse(managementEntity);
        return taxTypes.stream()
                .map(TaxMapper::map)
                .toList();
    }
}

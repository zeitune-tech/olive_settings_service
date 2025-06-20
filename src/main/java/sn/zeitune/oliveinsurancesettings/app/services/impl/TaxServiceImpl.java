package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Tax;
import sn.zeitune.oliveinsurancesettings.app.mappers.TaxMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxRepository;
import sn.zeitune.oliveinsurancesettings.app.services.TaxService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {

    private final TaxRepository repository;

    @Override
    public TaxResponse create(TaxRequest request, UUID managementEntity) {
        Tax entity = TaxMapper.map(request);
        entity.setManagementEntity(managementEntity);
        entity = repository.save(entity);
        return TaxMapper.map(entity);
    }


    @Override
    public TaxResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(TaxMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Tax not found"));
    }

    @Override
    public List<TaxResponse> getAll(UUID managementEntity) {
        // Assuming you want to fetch all taxes for a specific management entity
        List<Tax> taxes = repository.findAllByManagementEntity(managementEntity);
        return  taxes.stream()
                .map(TaxMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        Tax tax = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Tax not found"));

        tax.setDeleted(true);
        // Save the entity to mark it as deleted
        repository.save(tax);
    }

    @Override
    public TaxResponse update(UUID uuid, TaxRequest request, UUID managementEntity) {
        Tax tax = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Tax not found"));

        tax.setDesignation(request.designation());
        tax.setNature(request.nature());
        tax.setRgr(request.rgr());

        // Save the updated entity
        tax = repository.save(tax);
        return TaxMapper.map(tax);
    }
}

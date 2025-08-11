package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxCommissionsPointOfSaleRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxCommissionsPointOfSaleResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.TaxCommissionsPointOfSale;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.TaxCommissionMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxCommissionsPointOfSaleRepository;
import sn.zeitune.oliveinsurancesettings.app.services.TaxCommissionsPointOfSaleService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaxCommissionsPointOfSaleServiceImpl implements TaxCommissionsPointOfSaleService {

    private final TaxCommissionsPointOfSaleRepository repository;

    @Override
    public TaxCommissionsPointOfSaleResponse create(TaxCommissionsPointOfSaleRequest request) {
        TaxCommissionsPointOfSale entity = TaxCommissionMapper.toPointOfSaleEntity(request);
        entity.setDeleted(false);
        repository.save(entity);
        return TaxCommissionMapper.toPointOfSaleResponse(entity);
    }

    @Override
    public TaxCommissionsPointOfSaleResponse update(UUID uuid, TaxCommissionsPointOfSaleRequest request) {
        TaxCommissionsPointOfSale entity = repository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("Tax commission point of sale not found."));

        entity.setDateEffective(request.dateEffective());
        entity.setRate(request.rate());
        entity.setToWithhold(request.toWithhold());
        entity.setManagementEntity(request.managementEntity());
        entity.setPointOfSale(request.pointOfSaleId());
        entity.setPointOfSaleType(request.pointOfSaleType());

        repository.save(entity);
        return TaxCommissionMapper.toPointOfSaleResponse(entity);
    }

    @Override
    public TaxCommissionsPointOfSaleResponse getByUuid(UUID uuid) {
        TaxCommissionsPointOfSale entity = repository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("Tax commission point of sale not found."));

        return TaxCommissionMapper.toPointOfSaleResponse(entity);
    }

    @Override
    public List<TaxCommissionsPointOfSaleResponse> getAll(UUID managementEntity) {
        return repository.findAllByManagementEntityAndDeletedFalse(managementEntity).stream()
                .map(TaxCommissionMapper::toPointOfSaleResponse)
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        TaxCommissionsPointOfSale entity = repository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("Tax commission point of sale not found."));
        entity.setDeleted(true);
        repository.save(entity);
    }
}

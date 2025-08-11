package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxCommissionsContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxCommissionsContributorResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.TaxCommissionsContributor;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.TaxCommissionMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxCommissionsContributorRepository;
import sn.zeitune.oliveinsurancesettings.app.services.TaxCommissionsContributorService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaxCommissionsContributorServiceImpl implements TaxCommissionsContributorService {

    private final TaxCommissionsContributorRepository repository;

    @Override
    public TaxCommissionsContributorResponse create(TaxCommissionsContributorRequest request) {
        TaxCommissionsContributor entity = TaxCommissionMapper.toContributorEntity(request);
        entity.setDeleted(false);
        repository.save(entity);
        return TaxCommissionMapper.toContributorResponse(entity);
    }

    @Override
    public TaxCommissionsContributorResponse update(UUID uuid, TaxCommissionsContributorRequest request) {
        TaxCommissionsContributor entity = repository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("Tax commission contributor not found."));

        entity.setDateEffective(request.dateEffective());
        entity.setRate(request.rate());
        entity.setToWithhold(request.toWithhold());
        entity.setManagementEntity(request.managementEntity());
        entity.setContributor(request.contributorId());
        entity.setContributorType(request.contributorTypeId());

        repository.save(entity);
        return TaxCommissionMapper.toContributorResponse(entity);
    }

    @Override
    public TaxCommissionsContributorResponse getByUuid(UUID uuid) {
        TaxCommissionsContributor entity = repository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("Tax commission contributor not found."));

        return TaxCommissionMapper.toContributorResponse(entity);
    }

    @Override
    public List<TaxCommissionsContributorResponse> getAll(UUID managementEntity) {
        return repository.findAllByManagementEntityAndDeletedFalse(managementEntity).stream()
                .map(TaxCommissionMapper::toContributorResponse)
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        TaxCommissionsContributor entity = repository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("Tax commission contributor not found."));
        entity.setDeleted(true);
        repository.save(entity);
    }
}

package sn.zeitune.oliveinsurancesettings.app.services.settings.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ActivityCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ActivityPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ActivityUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ActivityResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Activity;
import sn.zeitune.oliveinsurancesettings.app.exceptions.ConflictException;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.ActivityMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.settings.ActivityRepository;
import sn.zeitune.oliveinsurancesettings.app.services.NormalizationUtils;
import sn.zeitune.oliveinsurancesettings.app.services.settings.ActivityService;
import sn.zeitune.oliveinsurancesettings.app.specifications.CommonSpecifications;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository repository;

    @Override
    public ActivityResponse create(ActivityCreateRequest request) {
        String code = NormalizationUtils.normalizeCode(request.code());
        String libelle = NormalizationUtils.normalizeLibelle(request.libelle());

        if (repository.existsByCodeAndDeletedFalse(code)) {
            throw new ConflictException("Code already exists");
        }
        if (repository.existsByLibelleIgnoreCaseAndDeletedFalse(libelle)) {
            throw new ConflictException("Libelle already exists");
        }

        Activity entity = Activity.builder()
                .code(code)
                .libelle(libelle)
                .actif(request.actif() == null ? true : request.actif())
                .build();
        entity = repository.save(entity);
        return ActivityMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ActivityResponse getByUuid(UUID uuid) {
        Activity entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Activity not found"));
        return ActivityMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityResponse> listAdmin(String q, Boolean actif, Boolean includeDeleted, Pageable pageable) {
        Specification<Activity> spec = Specification.where(CommonSpecifications.<Activity>includeDeleted(includeDeleted))
                .and(CommonSpecifications.<Activity>actifEquals(actif))
                .and(CommonSpecifications.<Activity>or(
                        CommonSpecifications.<Activity>containsInsensitive("code", q),
                        CommonSpecifications.<Activity>containsInsensitive("libelle", q)
                ));
        return repository.findAll(spec, pageable).map(ActivityMapper::map);
    }

    @Override
    public ActivityResponse update(UUID uuid, ActivityUpdateRequest request) {
        Activity entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Activity not found"));
        String code = NormalizationUtils.normalizeCode(request.code());
        String libelle = NormalizationUtils.normalizeLibelle(request.libelle());

        if (repository.existsByCodeAndDeletedFalseAndIdNot(code, entity.getId())) {
            throw new ConflictException("Code already exists");
        }
        if (repository.existsByLibelleIgnoreCaseAndDeletedFalseAndIdNot(libelle, entity.getId())) {
            throw new ConflictException("Libelle already exists");
        }

        entity.setCode(code);
        entity.setLibelle(libelle);
        entity.setActif(request.actif());
        return ActivityMapper.map(repository.save(entity));
    }

    @Override
    public ActivityResponse patch(UUID uuid, ActivityPatchRequest request) {
        Activity entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Activity not found"));
        if (request.code() != null) {
            String code = NormalizationUtils.normalizeCode(request.code());
            if (repository.existsByCodeAndDeletedFalseAndIdNot(code, entity.getId())) {
                throw new ConflictException("Code already exists");
            }
            entity.setCode(code);
        }
        if (request.libelle() != null) {
            String libelle = NormalizationUtils.normalizeLibelle(request.libelle());
            if (repository.existsByLibelleIgnoreCaseAndDeletedFalseAndIdNot(libelle, entity.getId())) {
                throw new ConflictException("Libelle already exists");
            }
            entity.setLibelle(libelle);
        }
        if (request.actif() != null) {
            entity.setActif(request.actif());
        }
        return ActivityMapper.map(repository.save(entity));
    }

    @Override
    public void delete(UUID uuid) {
        Activity entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Activity not found"));
        if (!entity.isDeleted()) {
            entity.setDeleted(true);
            repository.save(entity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityResponse> listInterService(String q, Pageable pageable) {
        Specification<Activity> spec = Specification.where(CommonSpecifications.<Activity>actifEquals(true))
                .and(CommonSpecifications.<Activity>includeDeleted(false))
                .and(CommonSpecifications.<Activity>or(
                        CommonSpecifications.<Activity>containsInsensitive("code", q),
                        CommonSpecifications.<Activity>containsInsensitive("libelle", q)
                ));
        return repository.findAll(spec, pageable).map(ActivityMapper::map);
    }
}


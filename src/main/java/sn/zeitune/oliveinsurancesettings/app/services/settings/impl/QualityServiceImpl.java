package sn.zeitune.oliveinsurancesettings.app.services.settings.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.QualityCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.QualityPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.QualityUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.QualityResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Quality;
import sn.zeitune.oliveinsurancesettings.app.exceptions.ConflictException;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.QualityMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.settings.QualityRepository;
import sn.zeitune.oliveinsurancesettings.app.services.NormalizationUtils;
import sn.zeitune.oliveinsurancesettings.app.services.settings.QualityService;
import sn.zeitune.oliveinsurancesettings.app.specifications.CommonSpecifications;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class QualityServiceImpl implements QualityService {

    private final QualityRepository repository;

    @Override
    public QualityResponse create(QualityCreateRequest request) {
        String code = NormalizationUtils.normalizeCode(request.code());
        String libelle = NormalizationUtils.normalizeLibelle(request.libelle());

        if (repository.existsByCodeAndDeletedFalse(code)) {
            throw new ConflictException("Code already exists");
        }
        if (repository.existsByLibelleIgnoreCaseAndDeletedFalse(libelle)) {
            throw new ConflictException("Libelle already exists");
        }

        Quality entity = Quality.builder()
                .code(code)
                .libelle(libelle)
                .actif(request.actif() == null ? true : request.actif())
                .build();
        entity = repository.save(entity);
        return QualityMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public QualityResponse getByUuid(UUID uuid) {
        Quality entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Quality not found"));
        return QualityMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QualityResponse> listAdmin(String q, Boolean actif, Boolean includeDeleted, Pageable pageable) {
        Specification<Quality> spec = Specification.<Quality>where(CommonSpecifications.includeDeleted(includeDeleted))
                .and(CommonSpecifications.actifEquals(actif))
                .and(CommonSpecifications.or(
                        CommonSpecifications.containsInsensitive("code", q),
                        CommonSpecifications.containsInsensitive("libelle", q)
                ));
        return repository.findAll(spec, pageable).map(QualityMapper::map);
    }

    @Override
    public QualityResponse update(UUID uuid, QualityUpdateRequest request) {
        Quality entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Quality not found"));
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
        return QualityMapper.map(repository.save(entity));
    }

    @Override
    public QualityResponse patch(UUID uuid, QualityPatchRequest request) {
        Quality entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Quality not found"));
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
        return QualityMapper.map(repository.save(entity));
    }

    @Override
    public void delete(UUID uuid) {
        Quality entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Quality not found"));
        if (!entity.isDeleted()) {
            entity.setDeleted(true);
            repository.save(entity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QualityResponse> listInterService(String q, Pageable pageable) {
        Specification<Quality> spec = Specification.<Quality>where(CommonSpecifications.actifEquals(true))
                .and(CommonSpecifications.includeDeleted(false))
                .and(CommonSpecifications.or(
                        CommonSpecifications.containsInsensitive("code", q),
                        CommonSpecifications.containsInsensitive("libelle", q)
                ));
        return repository.findAll(spec, pageable).map(QualityMapper::map);
    }
}


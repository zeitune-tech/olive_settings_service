package sn.zeitune.oliveinsurancesettings.app.services.settings.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CityCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CityPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CityUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CityResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.City;
import sn.zeitune.oliveinsurancesettings.app.exceptions.ConflictException;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.CityMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.settings.CityRepository;
import sn.zeitune.oliveinsurancesettings.app.services.NormalizationUtils;
import sn.zeitune.oliveinsurancesettings.app.services.settings.CityService;
import sn.zeitune.oliveinsurancesettings.app.specifications.CommonSpecifications;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository repository;

    @Override
    public CityResponse create(CityCreateRequest request) {
        String code = NormalizationUtils.normalizeCode(request.code());
        String libelle = NormalizationUtils.normalizeLibelle(request.libelle());

        if (repository.existsByCodeAndDeletedFalse(code)) {
            throw new ConflictException("Code already exists");
        }
        if (repository.existsByLibelleIgnoreCaseAndDeletedFalse(libelle)) {
            throw new ConflictException("Libelle already exists");
        }

        City entity = City.builder()
                .code(code)
                .libelle(libelle)
                .actif(request.actif() == null ? true : request.actif())
                .build();
        entity = repository.save(entity);
        return CityMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public CityResponse getByUuid(UUID uuid) {
        City entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("City not found"));
        return CityMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityResponse> listAdmin(String q, Boolean actif, Boolean includeDeleted, Pageable pageable) {
        Specification<City> spec = Specification.where(CommonSpecifications.<City>includeDeleted(includeDeleted))
                .and(CommonSpecifications.actifEquals(actif));

        // Only add search criteria if q is not null/empty
        if (q != null && !q.trim().isEmpty()) {
            spec = spec.and(CommonSpecifications.or(
                    CommonSpecifications.containsInsensitive("code", q),
                    CommonSpecifications.containsInsensitive("libelle", q)
            ));
        }

        return repository.findAll(spec, pageable).map(CityMapper::map);
    }

    @Override
    public CityResponse update(UUID uuid, CityUpdateRequest request) {
        City entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("City not found"));
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
        return CityMapper.map(repository.save(entity));
    }

    @Override
    public CityResponse patch(UUID uuid, CityPatchRequest request) {
        City entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("City not found"));
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
        return CityMapper.map(repository.save(entity));
    }

    @Override
    public void delete(UUID uuid) {
        City entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("City not found"));
        if (!entity.isDeleted()) {
            entity.setDeleted(true);
            repository.save(entity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityResponse> listInterService(String q, Pageable pageable) {
        Specification<City> spec = Specification.<City>where(CommonSpecifications.actifEquals(true))
                .and(CommonSpecifications.includeDeleted(false));

        // Only add search criteria if q is not null/empty
        if (q != null && !q.trim().isEmpty()) {
            spec = spec.and(CommonSpecifications.or(
                    CommonSpecifications.containsInsensitive("code", q),
                    CommonSpecifications.containsInsensitive("libelle", q)
            ));
        }

        return repository.findAll(spec, pageable).map(CityMapper::map);
    }
}


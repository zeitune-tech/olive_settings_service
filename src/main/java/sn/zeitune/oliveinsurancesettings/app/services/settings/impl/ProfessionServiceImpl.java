package sn.zeitune.oliveinsurancesettings.app.services.settings.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProfessionCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProfessionPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProfessionUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProfessionResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Profession;
import sn.zeitune.oliveinsurancesettings.app.exceptions.ConflictException;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProfessionMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.settings.ProfessionRepository;
import sn.zeitune.oliveinsurancesettings.app.services.NormalizationUtils;
import sn.zeitune.oliveinsurancesettings.app.services.settings.ProfessionService;
import sn.zeitune.oliveinsurancesettings.app.specifications.CommonSpecifications;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfessionServiceImpl implements ProfessionService {

    private final ProfessionRepository repository;

    @Override
    public ProfessionResponse create(ProfessionCreateRequest request) {
        String code = NormalizationUtils.normalizeCode(request.code());
        String libelle = NormalizationUtils.normalizeLibelle(request.libelle());

        if (repository.existsByCodeAndDeletedFalse(code)) {
            throw new ConflictException("Code already exists");
        }
        if (repository.existsByLibelleIgnoreCaseAndDeletedFalse(libelle)) {
            throw new ConflictException("Libelle already exists");
        }

        Profession entity = Profession.builder()
                .code(code)
                .libelle(libelle)
                .actif(request.actif() == null ? true : request.actif())
                .build();
        entity = repository.save(entity);
        return ProfessionMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfessionResponse getByUuid(UUID uuid) {
        Profession entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Profession not found"));
        return ProfessionMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfessionResponse> listAdmin(String q, Boolean actif, Boolean includeDeleted, Pageable pageable) {
        Specification<Profession> spec = Specification.<Profession>where(CommonSpecifications.includeDeleted(includeDeleted))
                .and(CommonSpecifications.actifEquals(actif));

        // Only add search criteria if q is not null/empty
        if (q != null && !q.trim().isEmpty()) {
            spec = spec.and(CommonSpecifications.or(
                    CommonSpecifications.containsInsensitive("code", q),
                    CommonSpecifications.containsInsensitive("libelle", q)
            ));
        }

        return repository.findAll(spec, pageable).map(ProfessionMapper::map);
    }

    @Override
    public ProfessionResponse update(UUID uuid, ProfessionUpdateRequest request) {
        Profession entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Profession not found"));
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
        return ProfessionMapper.map(repository.save(entity));
    }

    @Override
    public ProfessionResponse patch(UUID uuid, ProfessionPatchRequest request) {
        Profession entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Profession not found"));
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
        return ProfessionMapper.map(repository.save(entity));
    }

    @Override
    public void delete(UUID uuid) {
        Profession entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Profession not found"));
        if (!entity.isDeleted()) {
            entity.setDeleted(true);
            repository.save(entity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfessionResponse> listInterService(String q, Pageable pageable) {
        Specification<Profession> spec = Specification.<Profession>where(CommonSpecifications.actifEquals(true))
                .and(CommonSpecifications.includeDeleted(false));

        // Only add search criteria if q is not null/empty
        if (q != null && !q.trim().isEmpty()) {
            spec = spec.and(CommonSpecifications.or(
                    CommonSpecifications.containsInsensitive("code", q),
                    CommonSpecifications.containsInsensitive("libelle", q)
            ));
        }

        return repository.findAll(spec, pageable).map(ProfessionMapper::map);
    }
}


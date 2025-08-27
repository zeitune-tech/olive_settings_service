package sn.zeitune.oliveinsurancesettings.app.services.settings.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.GenreCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.GenrePatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.GenreUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.GenreResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Genre;
import sn.zeitune.oliveinsurancesettings.app.exceptions.ConflictException;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.GenreMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.settings.GenreRepository;
import sn.zeitune.oliveinsurancesettings.app.services.NormalizationUtils;
import sn.zeitune.oliveinsurancesettings.app.services.settings.GenreService;
import sn.zeitune.oliveinsurancesettings.app.specifications.CommonSpecifications;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    @Override
    public GenreResponse create(GenreCreateRequest request) {
        String code = NormalizationUtils.normalizeCode(request.code());
        String libelle = NormalizationUtils.normalizeLibelle(request.libelle());

        if (repository.existsByCodeAndDeletedFalse(code)) {
            throw new ConflictException("Code already exists");
        }
        if (repository.existsByLibelleIgnoreCaseAndDeletedFalse(libelle)) {
            throw new ConflictException("Libelle already exists");
        }

        Genre entity = Genre.builder()
                .code(code)
                .libelle(libelle)
                .actif(request.actif() == null || request.actif())
                .build();
        entity = repository.save(entity);
        return GenreMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public GenreResponse getByUuid(UUID uuid) {
        Genre entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Genre not found"));
        return GenreMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GenreResponse> listAdmin(String q, Boolean actif, Boolean includeDeleted, Pageable pageable) {
        Specification<Genre> spec = Specification.where(CommonSpecifications.<Genre>includeDeleted(includeDeleted))
                .and(CommonSpecifications.actifEquals(actif));

        if (q != null && !q.trim().isEmpty()) {
            spec = spec.and(CommonSpecifications.or(
                    CommonSpecifications.containsInsensitive("code", q),
                    CommonSpecifications.containsInsensitive("libelle", q)
            ));
        }

        return repository.findAll(spec, pageable).map(GenreMapper::map);
    }

    @Override
    public GenreResponse update(UUID uuid, GenreUpdateRequest request) {
        Genre entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Genre not found"));
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
        return GenreMapper.map(repository.save(entity));
    }

    @Override
    public GenreResponse patch(UUID uuid, GenrePatchRequest request) {
        Genre entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Genre not found"));
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
        return GenreMapper.map(repository.save(entity));
    }

    @Override
    public void delete(UUID uuid) {
        Genre entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Genre not found"));
        if (!entity.isDeleted()) {
            entity.setDeleted(true);
            repository.save(entity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GenreResponse> listInterService(String q, Pageable pageable) {
        Specification<Genre> spec = Specification.where(CommonSpecifications.<Genre>actifEquals(true))
                .and(CommonSpecifications.includeDeleted(false));

        if (q != null && !q.trim().isEmpty()) {
            spec = spec.and(CommonSpecifications.or(
                    CommonSpecifications.containsInsensitive("code", q),
                    CommonSpecifications.containsInsensitive("libelle", q)
            ));
        }

        return repository.findAll(spec, pageable).map(GenreMapper::map);
    }
}


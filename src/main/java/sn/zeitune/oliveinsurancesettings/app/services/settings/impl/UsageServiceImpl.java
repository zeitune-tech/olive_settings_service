package sn.zeitune.oliveinsurancesettings.app.services.settings.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.UsageCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.UsagePatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.UsageUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.UsageResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Genre;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Usage;
import sn.zeitune.oliveinsurancesettings.app.exceptions.ConflictException;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.UsageMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.settings.GenreRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.settings.UsageRepository;
import sn.zeitune.oliveinsurancesettings.app.services.NormalizationUtils;
import sn.zeitune.oliveinsurancesettings.app.services.settings.UsageService;
import sn.zeitune.oliveinsurancesettings.app.specifications.CommonSpecifications;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UsageServiceImpl implements UsageService {

    private final UsageRepository repository;
    private final GenreRepository genreRepository;

    @Override
    public UsageResponse create(UsageCreateRequest request) {
        Genre genre = genreRepository.findByUuid(request.genre())
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        String code = NormalizationUtils.normalizeCode(request.code());
        String libelle = NormalizationUtils.normalizeLibelle(request.libelle());

        if (repository.existsByGenreAndCodeAndDeletedFalse(genre, code)) {
            throw new ConflictException("Code already exists for this genre");
        }
        if (repository.existsByGenreAndLibelleIgnoreCaseAndDeletedFalse(genre, libelle)) {
            throw new ConflictException("Libelle already exists for this genre");
        }

        Usage entity = Usage.builder()
                .code(code)
                .libelle(libelle)
                .actif(request.actif() == null || request.actif())
                .genre(genre)
                .build();
        entity = repository.save(entity);
        return UsageMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public UsageResponse getByUuid(UUID uuid) {
        Usage entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Usage not found"));
        return UsageMapper.map(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsageResponse> listAdmin(String q, UUID genre, Boolean actif, Boolean includeDeleted, Pageable pageable) {
        Specification<Usage> spec = Specification.where(CommonSpecifications.<Usage>includeDeleted(includeDeleted))
                .and(CommonSpecifications.actifEquals(actif));

        if (genre != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("genre").get("uuid"), genre));
        }
        if (q != null && !q.trim().isEmpty()) {
            spec = spec.and(CommonSpecifications.or(
                    CommonSpecifications.containsInsensitive("code", q),
                    CommonSpecifications.containsInsensitive("libelle", q)
            ));
        }

        return repository.findAll(spec, pageable).map(UsageMapper::map);
    }

    @Override
    public UsageResponse update(UUID uuid, UsageUpdateRequest request) {
        Usage entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Usage not found"));
        Genre genre = genreRepository.findByUuid(request.genre())
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        String code = NormalizationUtils.normalizeCode(request.code());
        String libelle = NormalizationUtils.normalizeLibelle(request.libelle());

        if (repository.existsByGenreAndCodeAndDeletedFalseAndIdNot(genre, code, entity.getId())) {
            throw new ConflictException("Code already exists for this genre");
        }
        if (repository.existsByGenreAndLibelleIgnoreCaseAndDeletedFalseAndIdNot(genre, libelle, entity.getId())) {
            throw new ConflictException("Libelle already exists for this genre");
        }

        entity.setCode(code);
        entity.setLibelle(libelle);
        entity.setActif(request.actif());
        entity.setGenre(genre);
        return UsageMapper.map(repository.save(entity));
    }

    @Override
    public UsageResponse patch(UUID uuid, UsagePatchRequest request) {
        Usage entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Usage not found"));

        Genre genre = null;
        if (request.genre() != null) {
            genre = genreRepository.findByUuid(request.genre())
                    .orElseThrow(() -> new NotFoundException("Genre not found"));
        } else {
            genre = entity.getGenre();
        }

        if (request.code() != null) {
            String code = NormalizationUtils.normalizeCode(request.code());
            if (repository.existsByGenreAndCodeAndDeletedFalseAndIdNot(genre, code, entity.getId())) {
                throw new ConflictException("Code already exists for this genre");
            }
            entity.setCode(code);
        }
        if (request.libelle() != null) {
            String libelle = NormalizationUtils.normalizeLibelle(request.libelle());
            if (repository.existsByGenreAndLibelleIgnoreCaseAndDeletedFalseAndIdNot(genre, libelle, entity.getId())) {
                throw new ConflictException("Libelle already exists for this genre");
            }
            entity.setLibelle(libelle);
        }
        if (request.actif() != null) {
            entity.setActif(request.actif());
        }
        if (request.genre() != null) {
            entity.setGenre(genre);
        }
        return UsageMapper.map(repository.save(entity));
    }

    @Override
    public void delete(UUID uuid) {
        Usage entity = repository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Usage not found"));
        if (!entity.isDeleted()) {
            entity.setDeleted(true);
            repository.save(entity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsageResponse> listInterService(String q, UUID genre, Pageable pageable) {
        Specification<Usage> spec = Specification.where(CommonSpecifications.<Usage>actifEquals(true))
                .and(CommonSpecifications.includeDeleted(false));

        if (genre != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("genre").get("uuid"), genre));
        }
        if (q != null && !q.trim().isEmpty()) {
            spec = spec.and(CommonSpecifications.or(
                    CommonSpecifications.containsInsensitive("code", q),
                    CommonSpecifications.containsInsensitive("libelle", q)
            ));
        }

        return repository.findAll(spec, pageable).map(UsageMapper::map);
    }
}


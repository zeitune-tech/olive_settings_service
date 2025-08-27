package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.IncompatibleCoverageRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.IncompatibleCoverageResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.IncompatibleCoverage;
import sn.zeitune.oliveinsurancesettings.app.mappers.IncompatibleCoverageMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.IncompatibleCoverageRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.services.IncompatibleCoverageService;
import sn.zeitune.oliveinsurancesettings.app.specifications.IncompatibleCoverageSpecification;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IncompatibleCoverageServiceImpl implements IncompatibleCoverageService {

    private final IncompatibleCoverageRepository incompatibleCoverageRepository;
    private final CoverageRepository coverageRepository;
    private final ProductRepository productRepository;

    @Override
    public IncompatibleCoverageResponse create(IncompatibleCoverageRequest request, UUID managementEntity) {
        Coverage coverage = coverageRepository.findByUuid(request.coverageId())
                .orElseThrow(() -> new IllegalArgumentException("Coverage not found"));

        Coverage incompatible = coverageRepository.findByUuid(request.incompatibleId())
                .orElseThrow(() -> new IllegalArgumentException("Incompatible coverage not found"));

        incompatibleCoverageRepository.findByCoverageAndIncompatibleCoverageAndManagementEntity(coverage, incompatible, managementEntity)
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("This incompatibility already exists.");
                });

        incompatibleCoverageRepository.findByCoverageAndIncompatibleCoverageAndManagementEntity(incompatible, coverage, managementEntity)
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("This incompatibility already exists.");
                });

        IncompatibleCoverage entity = IncompatibleCoverageMapper.map(request, coverage, incompatible);
        entity.setManagementEntity(managementEntity);
        IncompatibleCoverage saved = incompatibleCoverageRepository.save(entity);

        return IncompatibleCoverageMapper.map(saved);
    }

    @Override
    public IncompatibleCoverageResponse getByUuid(UUID uuid) {
        IncompatibleCoverage entity = incompatibleCoverageRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("IncompatibleCoverage not found with UUID: " + uuid));
        return IncompatibleCoverageMapper.map(entity);
    }

    @Override
    public List<IncompatibleCoverageResponse> getAllIncompatibilitiesWith(UUID coverage, UUID managementEntity) {
        return null;
    }

    @Override
    public Page<IncompatibleCoverageResponse> getAll(UUID managementEntity, Pageable pageable) {
        return incompatibleCoverageRepository.findAllByManagementEntity(managementEntity, pageable)
                .map(IncompatibleCoverageMapper::map);
    }

    @Override
    public IncompatibleCoverageResponse update(UUID uuid, IncompatibleCoverageRequest request) {
        IncompatibleCoverage existing = incompatibleCoverageRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("IncompatibleCoverage not found with UUID: " + uuid));

        Coverage coverage = coverageRepository.findByUuid(request.coverageId())
                .orElseThrow(() -> new IllegalArgumentException("Coverage not found"));

        Coverage incompatible = coverageRepository.findByUuid(request.incompatibleId())
                .orElseThrow(() -> new IllegalArgumentException("Incompatible coverage not found"));

        existing.setCoverage(coverage);
        existing.setIncompatibleCoverage(incompatible);

        return IncompatibleCoverageMapper.map(incompatibleCoverageRepository.save(existing));
    }

    @Override
    public void delete(UUID uuid) {
        IncompatibleCoverage entity = incompatibleCoverageRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("IncompatibleCoverage not found with UUID: " + uuid));

        // Soft delete the entity
        entity.setDeleted(true);
        incompatibleCoverageRepository.save(entity);
    }

    public List<IncompatibleCoverageResponse> getByProductAndManagementEntity(
            UUID productUuid,
            UUID managementEntityUuid
    ) {
        Specification<IncompatibleCoverage> spec = IncompatibleCoverageSpecification.withFilters(
                productUuid,
                managementEntityUuid
        );
        return incompatibleCoverageRepository.findAll(spec).stream()
                .map(IncompatibleCoverageMapper::map)
                .collect(Collectors.toList());
    }
}

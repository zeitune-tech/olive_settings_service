package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageReferenceRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageReferenceResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.CoverageReference;
import sn.zeitune.oliveinsurancesettings.app.exceptions.ConflictException;
import sn.zeitune.oliveinsurancesettings.app.mappers.CoverageReferenceMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageReferenceRepository;
import sn.zeitune.oliveinsurancesettings.app.services.CoverageReferenceService;
import sn.zeitune.oliveinsurancesettings.app.templates.CoverageTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CoverageReferenceServiceImpl implements CoverageReferenceService {

    private final CoverageReferenceRepository coverageReferenceRepository;

    @Override
    public List<CoverageReferenceResponse> initCoverageReference(UUID managementEntity) {
        List<CoverageReferenceRequest> coverages = CoverageTemplate.coverageReferenceRequests();
        List<CoverageReference> existingReferences = coverageReferenceRepository.findAllByManagementEntity(managementEntity);
        List<CoverageReferenceRequest> newCoverages = coverages.stream()
                .filter(coverage -> existingReferences.stream()
                        .noneMatch(existing -> existing.getDesignation().equals(coverage.designation()) && existing.getManagementEntity().equals(managementEntity)
                ))
                .toList();
        List<CoverageReference> coverageReferences = newCoverages.stream()
                .map(coverage -> {
                    CoverageReference reference = CoverageReferenceMapper.map(coverage);
                    reference.setManagementEntity(managementEntity);
                    return reference;
                })
                .collect(Collectors.toList());
        coverageReferenceRepository.saveAll(coverageReferences);
        return coverageReferenceRepository.findAllByManagementEntity(managementEntity).stream()
                .map(CoverageReferenceMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public CoverageReferenceResponse create(
            CoverageReferenceRequest request,
            UUID managementEntity
    ) {

        CoverageReference existing = coverageReferenceRepository.findByDesignationAndManagementEntity(
                request.designation(),
                managementEntity
        );
        if (existing != null) {
            throw new ConflictException("CoverageReference with the same designation already exists");
        }

        CoverageReference ref = CoverageReferenceMapper.map(request);
        ref.setManagementEntity(managementEntity);
        CoverageReference saved = coverageReferenceRepository.save(ref);

        return CoverageReferenceMapper.map(saved);
    }

    @Override
    public CoverageReferenceResponse getByUuid(UUID uuid) {
        CoverageReference reference = coverageReferenceRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CoverageReference not found with UUID: " + uuid));
        return CoverageReferenceMapper.map(reference);
    }

    @Override
    public List<CoverageReferenceResponse> getAll(UUID managementEntity) {
        return coverageReferenceRepository.findAllByManagementEntity(managementEntity).stream()
                .map(CoverageReferenceMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public CoverageReferenceResponse update(UUID uuid, CoverageReferenceRequest request) {
        CoverageReference existing = coverageReferenceRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CoverageReference not found with UUID: " + uuid));

        existing.setDesignation(request.designation());
        existing.setFamily(request.family());
        existing.setAccessCharacteristic(request.accessCharacteristic());
        existing.setTariffAccess(request.tariffAccess());

        return CoverageReferenceMapper.map(coverageReferenceRepository.save(existing));
    }

    @Override
    public void delete(UUID uuid) {
        CoverageReference entity = coverageReferenceRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CoverageReference not found with UUID: " + uuid));

        // Soft delete the entity
        entity.setDeleted(true);
        coverageReferenceRepository.save(entity);
    }
}

package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.CoveragesRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.CoverageReference;
import sn.zeitune.oliveinsurancesettings.app.mappers.CoverageMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageReferenceRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageRepository;
import sn.zeitune.oliveinsurancesettings.app.services.CoverageService;
import sn.zeitune.oliveinsurancesettings.app.specifications.CoverageSpecification;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CoverageServiceImpl implements CoverageService {

    private final CoverageRepository coverageRepository;
    private final CoverageReferenceRepository coverageReferenceRepository;


    @Override
    public CoverageResponse getByUuid(UUID uuid) {
        Coverage coverage = coverageRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Coverage not found with UUID: " + uuid));
        return CoverageMapper.map(coverage);
    }

    @Override
    public List<CoverageResponse> getAll() {
        return coverageRepository.findAll().stream()
                .map(CoverageMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public CoverageResponse update(UUID uuid, CoverageRequest request) {
        Coverage existing = coverageRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Coverage not found with UUID: " + uuid));

        // Met à jour les champs
        existing.setNature(request.nature());
        existing.setFree(request.isFree());
        existing.setFixed(request.isFixed());
        existing.setCalculationMode(request.calculationMode());
        existing.setFixedCapital(request.fixedCapital());
        existing.setMinCapital(request.minCapital());
        existing.setMaxCapital(request.maxCapital());
        existing.setOrder(request.order());
        existing.setProrata(request.prorata());
        existing.setDisplayPrime(request.displayPrime());
        existing.setGeneratesCharacteristic(request.generatesCharacteristic());

        return CoverageMapper.map(coverageRepository.save(existing));
    }

    @Override
    public Page<CoverageResponse> search(
            String nature,
            String designation,
            Boolean isFree,
            Boolean isFixed,
            CalculationMode calculationMode,
            UUID productUuid,
            UUID managementEntityUuid,
            Pageable pageable
    ) {
        return coverageRepository
                .findAll(CoverageSpecification.withFilters(nature, designation, isFree, isFixed, calculationMode, productUuid, managementEntityUuid), pageable)
                .map(CoverageMapper::map);
    }

    @Override
    public void delete(UUID uuid) {
        Coverage coverage = coverageRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Coverage not found with UUID: " + uuid));
        coverageRepository.delete(coverage);
    }

    @Override
    public List<CoverageResponse> getByProductUuid(UUID productUuid, UUID managementEntity) {
        return coverageRepository.findAllByProductAndManagementEntity(productUuid, managementEntity)
                .stream()
                .map(CoverageMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<CoverageResponse> createCoverages(UUID managementEntity, CoveragesRequest request) {
        Set<CoverageReference> coverageReferences = coverageReferenceRepository.findAllByUuidIn(request.coverages());

        Set<Coverage> coverages = coverageReferences.stream()
                .map(coverageReference -> Coverage.builder()
                        .coverageReference(coverageReference)
                        .product(request.product())
                        .managementEntity(managementEntity)
                        .build()).collect(Collectors.toSet());
        return coverageRepository.saveAll(coverages)
                .stream()
                .map(CoverageMapper::map)
                .collect(Collectors.toList());
    }
}

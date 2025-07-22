package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.CoveragesRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageReference;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.mappers.BranchMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.CoverageMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageReferenceRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.services.CoverageService;
import sn.zeitune.oliveinsurancesettings.app.specifications.CoverageSpecification;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CoverageServiceImpl implements CoverageService {

    private final CoverageRepository coverageRepository;
    private final CoverageReferenceRepository coverageReferenceRepository;
    private final ProductRepository productRepository;
    private final AdministrationClient administrationClient;


    @Override
    public CoverageResponse getByUuid(UUID uuid) {
        Coverage coverage = coverageRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Coverage not found with UUID: " + uuid));


        return CoverageMapper.map(coverage, ProductMapper.map(coverage.getProduct()));
    }

    @Override
    public List<CoverageResponse> getAll(UUID managementEntity) {
        Specification<Coverage> spec = CoverageSpecification.managementEntityEquals(managementEntity);
        List<Coverage> coverages = coverageRepository.findAll(spec);

        // Fetch Management Entity details
        List<ManagementEntityResponse> managementEntities = administrationClient.getManagementEntities(
                coverages.stream()
                        .map(Coverage::getManagementEntity)
                        .collect(Collectors.toList())
        );

        // Map Management Entity details to CoverageResponse
        Map<UUID, ManagementEntityResponse> managementEntityMap = managementEntities.stream()
                .collect(Collectors.toMap(ManagementEntityResponse::id, entity -> entity));

        // Fetch products for the management entity
        return coverages.stream()
                .map(coverage -> CoverageMapper.map(coverage,
                        ProductMapper.map(coverage.getProduct(),
                                BranchMapper.map(coverage.getProduct().getBranch()),
                                managementEntityMap.get(coverage.getProduct().getOwner())
                        ),
                        managementEntityMap.get(coverage.getManagementEntity())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CoverageResponse update(UUID uuid, CoverageRequest request) {
        Coverage existing = coverageRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Coverage not found with UUID: " + uuid));

        // Met à jour les champs
        existing.setNature(request.nature());
        existing.setFree(request.isFree());
        existing.setFlatRate(request.isFlatRate());
        existing.setCalculationMode(request.calculationMode());
        existing.setFixedCapital(request.fixedCapital());
        existing.setMinCapital(request.minCapital());
        existing.setMaxCapital(request.maxCapital());
        existing.setOrder(request.order());
        existing.setProrata(request.prorata());
        existing.setDisplayPrime(request.displayPrime());
        existing.setGeneratesCharacteristic(request.generatesCharacteristic());

        return CoverageMapper.map(coverageRepository.save(existing), ProductMapper.map(existing.getProduct()));
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
                .map(coverage -> CoverageMapper.map(coverage, ProductMapper.map(coverage.getProduct())));
    }

    @Override
    public void delete(UUID uuid) {
        Coverage coverage = coverageRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Coverage not found with UUID: " + uuid));

        // Soft delete the coverage
        coverage.setDeleted(true);
        coverageRepository.save(coverage);
    }

    @Override
    public List<CoverageResponse> getByProductUuid(UUID productUuid, UUID managementEntity) {

        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with UUID: " + productUuid));




        return coverageRepository.findAllByProductAndManagementEntity(product, managementEntity)
                .stream()
                .map(coverage -> CoverageMapper.map(
                        coverage,
                        ProductMapper.map(coverage.getProduct(), BranchMapper.map(coverage.getProduct().getBranch()),
                                ManagementEntityResponse.builder().id(managementEntity).build())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<CoverageResponse> createCoverages(UUID managementEntity, CoveragesRequest request) {
        Set<CoverageReference> coverageReferences = coverageReferenceRepository.findAllByUuidInAndDeletedIsFalse(request.coverages());

        Set<Coverage> coverages = coverageReferences.stream()
                .map(coverageReference -> Coverage.builder()
                        .coverageReference(coverageReference)
                        .managementEntity(managementEntity)
                        .build()).collect(Collectors.toSet());

        // Validate that the product exists in the product repository
        Product product = productRepository.findByUuid(request.product())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with UUID: " + request.product()));

        // Set the product for each coverage
        coverages.forEach(coverage -> coverage.setProduct(product));

        return coverageRepository.saveAll(coverages)
                .stream()
                .map(coverage -> CoverageMapper.map(coverage, ProductMapper.map(coverage.getProduct())))
                .collect(Collectors.toList());
    }
}

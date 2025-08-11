package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductCoveragesUpdate;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductUpdate;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.BranchResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Branch;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageReference;
import sn.zeitune.oliveinsurancesettings.app.entities.product.PrivateProduct;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.entities.product.PublicProduct;
import sn.zeitune.oliveinsurancesettings.app.exceptions.BusinessException;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.BranchMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.*;
import sn.zeitune.oliveinsurancesettings.app.services.ProductService;
import sn.zeitune.oliveinsurancesettings.app.specifications.ProductSpecification;
import sn.zeitune.oliveinsurancesettings.enums.ManagementEntityType;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final AdministrationClient administrationClient;
    private final CoverageRepository coverageRepository;
    private final CoverageReferenceRepository coverageReferenceRepository;
    private final BranchRepository branchRepository;



    @Override
    public ProductResponse createProduct(ProductRequestDTO dto, UUID ownerUuid) {

        // Fetch the owner entity
        ManagementEntityResponse owner = administrationClient.findManagementEntityByUuid(ownerUuid)
                .orElseThrow(() -> new NotFoundException("Management entity not found"));

        Branch branch = branchRepository.findByUuid(dto.branch())
                .orElseThrow(() -> new NotFoundException("Branch not found"));

        Product product;

        if (owner.type() == ManagementEntityType.MARKET_LEVEL_ORGANIZATION ) {

            product = PublicProduct.builder()
                    .name(dto.name())
                    .description(dto.description())
                    .fleet(dto.fleet())
                    .hasReduction(dto.hasReduction())
                    .branch(branch)
                    .minRisk(dto.minRisk())
                    .maxRisk(dto.maxRisk())
                    .minimumGuaranteeNumber(dto.minimumGuaranteeNumber())
                    .owner(ownerUuid)
                    .build();
        } else {
            product = PrivateProduct.builder()
                    .name(dto.name())
                    .description(dto.description())
                    .fleet(dto.fleet())
                    .hasReduction(dto.hasReduction())
                    .branch(branch)
                    .minRisk(dto.minRisk())
                    .maxRisk(dto.maxRisk())
                    .minimumGuaranteeNumber(dto.minimumGuaranteeNumber())
                    .owner(ownerUuid)
                    .build();
        }

        if (productRepository.existsByNameAndBranchAndOwner(dto.name(), branch, ownerUuid)) {
            throw new BusinessException("Product with the same name already exists in this branch and owner.");
        }

        ProductResponse productResponse = ProductMapper.map(productRepository.save(product), BranchMapper.map(branch), owner);

        log.info("Product created successfully: {}", productResponse.id());

        // create coverages
        try {
            Set<UUID> coverageReferences = dto.coverages() != null ? dto.coverages() : new HashSet<>();
            if (coverageReferences.isEmpty()) {
                throw new BusinessException("No coverages provided for product creation");
            }

            Set<CoverageReference> references = coverageReferenceRepository.findAllByUuidInAndDeletedIsFalse(coverageReferences);
            if (references.isEmpty()) {
                throw new NotFoundException("No valid coverage references found for the provided UUIDs");
            }

            for (CoverageReference reference : references) {
                Coverage coverage = new Coverage();
                coverage.setCoverageReference(reference);
                coverage.setProduct(product);
                coverage.setManagementEntity(ownerUuid);
                coverageRepository.save(coverage);
            }
        } catch (Exception e) {
            log.error("Error creating coverages for product {}: {}", productResponse.id(), e.getMessage());
            throw new BusinessException("Error creating coverages for product");
        }

        return productResponse;
    }

    @Override
    public ProductResponse updateProduct(UUID uuid, ProductUpdate dto) {
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setFleet(dto.fleet());
        product.setHasReduction(dto.hasReduction());
        product.setMinRisk(dto.minRisk());
        product.setMaxRisk(dto.maxRisk());
        product.setMinimumGuaranteeNumber(dto.minimumGuaranteeNumber());

        return ProductMapper.map(productRepository.save(product));
    }

    @Override
    public ProductResponse addCoverageToProduct(UUID productUuid, ProductCoveragesUpdate productCoverages) {
        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (productCoverages.coverages() == null || productCoverages.coverages().isEmpty()) {
            log.warn("No coverages provided for product {}", product.getUuid());
            return ProductMapper.map(product);
        }

        Set<CoverageReference> references = coverageReferenceRepository.findAllByUuidInAndDeletedIsFalse(productCoverages.coverages());
        if (references.isEmpty()) {
            log.warn("No valid coverage references found for product {}", product.getUuid());
            return ProductMapper.map(product);
        }

        // Create new coverages
        for (CoverageReference reference : references) {
            Coverage coverage = new Coverage();
            coverage.setCoverageReference(reference);
            coverage.setProduct(product);
            coverage.setManagementEntity(product.getOwner());
            coverageRepository.save(coverage);
        }

        return ProductMapper.map(productRepository.save(product));
    }

    @Override
    public ProductResponse removeCoverageFromProduct(UUID productUuid, ProductCoveragesUpdate productCoverages) {
        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (productCoverages.coverages() == null || productCoverages.coverages().isEmpty()) {
            log.warn("No coverages provided for product {}", product.getUuid());
            return ProductMapper.map(product);
        }

        Set<CoverageReference> references = coverageReferenceRepository.findAllByUuidInAndDeletedIsFalse(productCoverages.coverages());
        if (references.isEmpty()) {
            log.warn("No valid coverage references found for product {}", product.getUuid());
            return ProductMapper.map(product);
        }

        // Remove specified coverages
        for (CoverageReference reference : references) {
            Coverage coverage = coverageRepository.findByProductAndCoverageReference(product, reference)
                    .orElseThrow(() -> new NotFoundException("Coverage not found for the specified reference"));
            coverage.setDeleted(true);
            coverageRepository.save(coverage);
        }

        return ProductMapper.map(productRepository.save(product));
    }


    @Override
    public void sharePublicProductWithCompanies(UUID productUuid, List<UUID> companyUuids) {
        PublicProduct product = (PublicProduct) productRepository.findByUuid(productUuid)
                .filter(p -> p instanceof PublicProduct)
                .orElseThrow(() -> new NotFoundException("Public product not found"));

        List<ManagementEntityResponse> companies = administrationClient.getManagementEntities(companyUuids);
        if (companies.isEmpty()) throw new NotFoundException("No valid companies found");

        product.getSharedWithCompanies().addAll(companies.stream().map(ManagementEntityResponse::id).collect(Collectors.toSet()));
        productRepository.save(product);
    }

    @Override
    public ProductResponse getByUuid(UUID uuid) {
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return ProductMapper.map(product);
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getByManagementEntityUuid(UUID uuid) {

        Specification<Product> specification = ProductSpecification.ownerEquals(uuid);

        List<Product> products = productRepository.findAll(specification);

        List<ManagementEntityResponse> managementEntities = administrationClient.getManagementEntities(
                products.stream()
                .map(Product::getOwner)
                .distinct()
                .collect(Collectors.toList())
        );

        Map<UUID, ManagementEntityResponse> managementEntityMap = managementEntities.stream()
                .collect(Collectors.toMap(ManagementEntityResponse::id, entity -> entity));

        return products.stream()
                .map(product -> {
                    ManagementEntityResponse owner = managementEntityMap.get(product.getOwner());
                    BranchResponseDTO branch = BranchMapper.map(product.getBranch());
                    return ProductMapper.map(product, branch, owner);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllOnBranch(UUID managementEntityUuid, UUID branchUuid) {
        return productRepository.findAllByOwnerAndBranch(
                managementEntityUuid,
                branchRepository.findByUuid(branchUuid)
                        .orElseThrow(() -> new NotFoundException("Branch not found"))
        ).stream()
                .filter(product -> !product.isDeleted())
                .map(ProductMapper::map)
                .collect(Collectors.toList()
        );
    }

    @Override
    public Page<ProductResponse> search(
            String name,
            String branchName,
            Integer minRisk,
            Integer maxRisk,
            Boolean fleet,
            Boolean hasReduction,
            Pageable pageable,
            UUID ownerUuid
    ) {
        return null;
    }

    @Override
    public List<ProductResponse> getByManagementEntityUuids(List<UUID> uuids) {
        return productRepository.findAllByUuidIn(uuids).stream()
                .filter(product -> !product.isDeleted())
                .map(ProductMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(UUID uuid) {
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Set<Coverage> coverages = coverageRepository.findAllByProduct(product);

        coverages.forEach(coverage -> {
            coverage.setDeleted(true);
            coverageRepository.save(coverage);
        });

        // Soft delete the product
        product.setDeleted(true);
        productRepository.save(product);
    }

}

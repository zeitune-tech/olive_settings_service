package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.BaseTaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.BaseTaxResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseTax;
import sn.zeitune.oliveinsurancesettings.app.entities.CoverageReference;
import sn.zeitune.oliveinsurancesettings.app.entities.Tax;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.mappers.BaseTaxMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.CoverageReferenceMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.TaxMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.BaseTaxRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageReferenceRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxRepository;
import sn.zeitune.oliveinsurancesettings.app.services.BaseTaxService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BaseTaxServiceImpl implements BaseTaxService {

    private final BaseTaxRepository repository;
    private final TaxRepository taxRepository;
    private final CoverageReferenceRepository coverageRepository;

    private final ProductRepository productRepository;

    @Override
    public BaseTaxResponse create(BaseTaxRequest request, UUID managementEntity) {
        BaseTax entity = BaseTaxMapper.map(request);
        entity.setManagementEntity(managementEntity);

        // Get Tax entity from the repository
        Tax tax = taxRepository.findByUuid(request.taxId())
                .orElseThrow(() -> new IllegalArgumentException("Tax not found"));

        // Set the Tax and Coverage entities in the BaseTax entity
        entity.setTax(tax);
        // Validate that the product exists in the product repository
        Optional<Product> product = productRepository.findByUuid(request.productId());
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }
        entity.setProduct(product.get());

        // Get Coverage entity from the repository
        CoverageReference coverage = coverageRepository.findByUuid(request.coverageId())
                .orElseThrow(() -> new IllegalArgumentException("Coverage not found"));
        entity.setCoverage(coverage);

        entity = repository.save(entity);
        return BaseTaxMapper.map(entity, TaxMapper.map(tax), CoverageReferenceMapper.map(coverage),
                ProductMapper.map(product.get()));
    }


    @Override
    public BaseTaxResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(bTax -> BaseTaxMapper.map(bTax, TaxMapper.map(bTax.getTax()), CoverageReferenceMapper.map(bTax.getCoverage()),
                        ProductMapper.map(bTax.getProduct())))
                .orElseThrow(() -> new IllegalArgumentException("BaseTax not found"));
    }

    @Override
    public List<BaseTaxResponse> getAll(UUID managementEntity) {
        // Assuming you want to fetch all base taxes for a specific management entity
        List<BaseTax> baseTaxes = repository.findAllByManagementEntity(managementEntity);

        // Map each base tax to its corresponding product
        return baseTaxes.stream()
                .map(baseTax -> {
                    Tax tax = baseTax.getTax();
                    CoverageReference coverage = baseTax.getCoverage();
                    return BaseTaxMapper.map(baseTax, TaxMapper.map(tax), CoverageReferenceMapper.map(coverage),
                            ProductMapper.map(baseTax.getProduct()));
                })
                .collect(Collectors.toList());


    }

    @Override
    public void delete(UUID uuid) {
        BaseTax entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("BaseTax not found"));

        // Mark the entity as deleted
        entity.setDeleted(true);
        repository.save(entity);
    }

    @Override
    public BaseTaxResponse update(UUID uuid, BaseTaxRequest request, UUID managementEntity) {
        BaseTax baseTax = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("BaseTax not found"));

        // Validate that the management entity matches
        if (!baseTax.getManagementEntity().equals(managementEntity)) {
            throw new IllegalArgumentException("BaseTax does not belong to the specified management entity");
        }

        // Validate that the tax exists in the tax repository
        Tax tax = taxRepository.findByUuid(request.taxId())
                .orElseThrow(() -> new IllegalArgumentException("Tax not found"));
        baseTax.setTax(tax);

        // Validate that the product exists in the product repository
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        baseTax.setProduct(product);

        // Validate that the coverage exists in the coverage repository
        CoverageReference coverage = coverageRepository.findByUuid(request.coverageId())
                .orElseThrow(() -> new IllegalArgumentException("Coverage not found"));
        baseTax.setCoverage(coverage);

        // Update other fields
        baseTax.setCalculationBase(request.calculationBase());
        baseTax.setRate(request.rate());
        baseTax.setFixedAmount(request.fixedAmount());
        baseTax.setFlat(request.isFlat());

        baseTax = repository.save(baseTax);
        return BaseTaxMapper.map(baseTax, TaxMapper.map(tax), CoverageReferenceMapper.map(coverage),
                ProductMapper.map(product));
    }
}

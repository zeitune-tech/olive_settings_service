package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.BaseTaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.BaseTaxResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseTax;
import sn.zeitune.oliveinsurancesettings.app.entities.CoverageReference;
import sn.zeitune.oliveinsurancesettings.app.entities.Tax;
import sn.zeitune.oliveinsurancesettings.app.mappers.BaseTaxMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.CoverageReferenceMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.TaxMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.BaseTaxRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageReferenceRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxRepository;
import sn.zeitune.oliveinsurancesettings.app.services.BaseTaxService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BaseTaxServiceImpl implements BaseTaxService {

    private final BaseTaxRepository repository;
    private final TaxRepository taxRepository;
    private final CoverageReferenceRepository coverageRepository;

    private final AdministrationClient administrationClient;

    @Override
    public BaseTaxResponse create(BaseTaxRequest request, UUID managementEntity) {
        BaseTax entity = BaseTaxMapper.map(request);
        entity.setManagementEntity(managementEntity);

        // Get Tax entity from the repository
        Tax tax = taxRepository.findByUuid(request.taxId())
                .orElseThrow(() -> new IllegalArgumentException("Tax not found"));

        // Set the Tax and Coverage entities in the BaseTax entity
        entity.setTax(tax);
        entity.setProduct(request.productId());

        CoverageReference coverage = null;

        if (request.coverageId() != null) {
            // Get Coverage entity from the repository
            coverage = coverageRepository.findByUuid(request.coverageId())
                    .orElseThrow(() -> new IllegalArgumentException("Coverage not found"));
            entity.setCoverage(coverage);
        }

        entity = repository.save(entity);
        return BaseTaxMapper.map(entity, TaxMapper.map(tax), CoverageReferenceMapper.map(coverage), ProductResponseDTO.builder().build());
    }


    @Override
    public BaseTaxResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(bTax -> BaseTaxMapper.map(bTax, TaxMapper.map(bTax.getTax()), CoverageReferenceMapper.map(bTax.getCoverage()), ProductResponseDTO.builder().build()))
                .orElseThrow(() -> new IllegalArgumentException("BaseTax not found"));
    }

    @Override
    public List<BaseTaxResponse> getAll(UUID managementEntity) {
        // Assuming you want to fetch all base taxes for a specific management entity
        List<BaseTax> baseTaxes = repository.findAllByManagementEntity(managementEntity);

        Map<UUID, ProductResponseDTO> products;

        // Fetch products for the management entity
        List<ProductResponseDTO> productResponseDTOS = administrationClient.getByManagementEntity(managementEntity);

        // Map the products to a UUID to ProductResponseDTO map
        products = productResponseDTOS.stream()
                .collect(Collectors.toMap(ProductResponseDTO::id, product -> product));

        // Map each base tax to its corresponding product
        return baseTaxes.stream()
                .map(baseTax -> {
                    Tax tax = baseTax.getTax();
                    CoverageReference coverage = baseTax.getCoverage();
                    ProductResponseDTO product = products.get(baseTax.getProduct());
                    return BaseTaxMapper.map(baseTax, TaxMapper.map(tax), CoverageReferenceMapper.map(coverage), product);
                })
                .collect(Collectors.toList());


    }

    @Override
    public void delete(UUID uuid) {
        BaseTax entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("BaseTax not found"));
        repository.delete(entity);
    }
}

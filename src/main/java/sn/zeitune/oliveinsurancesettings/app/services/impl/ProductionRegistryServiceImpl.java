package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductionRegistryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductionRegistryResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.ProductionRegistry;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductionRegistryMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductionRegistryRepository;
import sn.zeitune.oliveinsurancesettings.app.services.ProductionRegistryService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionRegistryServiceImpl implements ProductionRegistryService {

    private final ProductionRegistryRepository productionRegistryRepository;
    private final AdministrationClient administrationClient;
    private final ProductRepository productRepository;

    @Override
    public ProductionRegistryResponse create(ProductionRegistryRequest request, UUID managementEntity) {
        ProductionRegistry entity = ProductionRegistryMapper.map(request);
        entity.setManagementEntity(managementEntity);

        // Validate that the product exists in the administration client
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with UUID: " + request.productId()));
        entity.setProduct(product);

        ProductionRegistry saved = productionRegistryRepository.save(entity);
        return ProductionRegistryMapper.map(saved,
                ProductMapper.map(product));
    }

    @Override
    public ProductionRegistryResponse getByUuid(UUID uuid) {
        ProductionRegistry entity = productionRegistryRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("ProductionRegistry not found with UUID: " + uuid));
        return ProductionRegistryMapper.map(entity,
                ProductMapper.map(entity.getProduct()));
    }

    @Override
    public Page<ProductionRegistryResponse> getAll(UUID managementEntity, Pageable pageable) {
        return productionRegistryRepository.findAllByManagementEntity(managementEntity, pageable)
                .map(registry -> ProductionRegistryMapper.map(registry,
                        ProductMapper.map(registry.getProduct())));
    }

    @Override
    public List<ProductionRegistryResponse> getAll(UUID managementEntity) {

        List<ProductionRegistry> registries = productionRegistryRepository.findAllByManagementEntity(managementEntity);


        return registries.stream()
                .map(registry -> ProductionRegistryMapper.map(registry, ProductMapper.map(registry.getProduct())))
                .collect(Collectors.toList());
    }

    @Override
    public ProductionRegistryResponse update(UUID uuid, ProductionRegistryRequest request) {
        ProductionRegistry existing = productionRegistryRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("ProductionRegistry not found with UUID: " + uuid));

        existing.setPrefix(request.prefix());
        existing.setLength(request.length());

        return ProductionRegistryMapper.map(productionRegistryRepository.save(existing),
                ProductMapper.map(existing.getProduct()));
    }

    @Override
    public void delete(UUID uuid) {
        ProductionRegistry entity = productionRegistryRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("ProductionRegistry not found with UUID: " + uuid));

        // Soft delete the entity
        entity.setDeleted(true);
        productionRegistryRepository.save(entity);
    }
}

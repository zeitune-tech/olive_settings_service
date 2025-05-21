package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductionRegistryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductionRegistryResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.ProductionRegistry;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductionRegistryMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductionRegistryRepository;
import sn.zeitune.oliveinsurancesettings.app.services.ProductionRegistryService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionRegistryServiceImpl implements ProductionRegistryService {

    private final ProductionRegistryRepository productionRegistryRepository;

    @Override
    public ProductionRegistryResponse create(ProductionRegistryRequest request, UUID managementEntity) {
        ProductionRegistry entity = ProductionRegistryMapper.map(request);
        entity.setManagementEntity(managementEntity);
        ProductionRegistry saved = productionRegistryRepository.save(entity);
        return ProductionRegistryMapper.map(saved);
    }

    @Override
    public ProductionRegistryResponse getByUuid(UUID uuid) {
        ProductionRegistry entity = productionRegistryRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("ProductionRegistry not found with UUID: " + uuid));
        return ProductionRegistryMapper.map(entity);
    }

    @Override
    public Page<ProductionRegistryResponse> getAll(UUID managementEntity, Pageable pageable) {
        return productionRegistryRepository.findAllByManagementEntity(managementEntity, pageable)
                .map(ProductionRegistryMapper::map);
    }

    @Override
    public ProductionRegistryResponse update(UUID uuid, ProductionRegistryRequest request) {
        ProductionRegistry existing = productionRegistryRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("ProductionRegistry not found with UUID: " + uuid));

        existing.setPrefix(request.prefix());
        existing.setLength(request.length());

        return ProductionRegistryMapper.map(productionRegistryRepository.save(existing));
    }

    @Override
    public void delete(UUID uuid) {
        ProductionRegistry entity = productionRegistryRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("ProductionRegistry not found with UUID: " + uuid));
        productionRegistryRepository.delete(entity);
    }
}

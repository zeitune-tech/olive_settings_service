package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.AccessoryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.AccessoryResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Accessory;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.mappers.AccessoryMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.AccessoryRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.services.AccessoryService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository repository;
    private final AdministrationClient administrationClient;
    private final ProductRepository productRepository;

    @Override
    public AccessoryResponse create(AccessoryRequest request, UUID managementEntity) {
        Accessory entity = AccessoryMapper.map(request);
        entity.setManagementEntity(managementEntity);

        // Validate that the product exists in the product repository
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        entity.setProduct(product);

        entity = repository.save(entity);
        return AccessoryMapper.map(entity, ProductMapper.map(product));
    }


    @Override
    public AccessoryResponse getByUuid(UUID uuid) {


        return repository.findByUuid(uuid)
                .map( accessory -> AccessoryMapper.map(accessory,
                        ProductMapper.map(accessory.getProduct())))
                .orElseThrow(() -> new IllegalArgumentException("Accessory not found"));
    }

    @Override
    public List<AccessoryResponse> getAll(UUID managementEntity) {
        // Assuming you want to fetch all accessories for a specific management entity
        List<Accessory> accessories = repository.findAllByManagementEntity(managementEntity);

        // Map each accessory to its corresponding product
        return accessories.stream()
                .map(accessory -> AccessoryMapper.map(accessory,
                        ProductMapper.map(accessory.getProduct())))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        Accessory accessory = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Accessory not found"));

        // Delete the accessory
        accessory.setDeleted(true);
        repository.save(accessory);
    }

    @Override
    public AccessoryResponse update(UUID uuid, AccessoryRequest request, UUID managementEntity) {
        Accessory accessory = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Accessory not found"));

        // Validate that the management entity matches
        if (!accessory.getManagementEntity().equals(managementEntity)) {
            throw new IllegalArgumentException("Accessory does not belong to the specified management entity");
        }

        // Validate that the product exists in the product repository
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        accessory.setProduct(product);

        // Update the accessory fields
        accessory.setAccessoryAmount(request.accessoryAmount());
        accessory.setDateEffective(request.dateEffective());
        accessory.setActType(request.actType());

        // Save the updated accessory
        accessory = repository.save(accessory);

        return AccessoryMapper.map(accessory, ProductMapper.map(product));
    }
}

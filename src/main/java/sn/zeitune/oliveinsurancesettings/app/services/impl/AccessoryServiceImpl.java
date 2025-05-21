package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.AccessoryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.AccessoryResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Accessory;
import sn.zeitune.oliveinsurancesettings.app.mappers.AccessoryMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.AccessoryRepository;
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

    @Override
    public AccessoryResponse create(AccessoryRequest request, UUID managementEntity) {
        Accessory entity = AccessoryMapper.map(request);
        entity.setManagementEntity(managementEntity);
        entity = repository.save(entity);
        return AccessoryMapper.map(entity, ProductResponseDTO.builder().id(entity.getProduct()).build());
    }


    @Override
    public AccessoryResponse getByUuid(UUID uuid) {


        return repository.findByUuid(uuid)
                .map( accessory -> AccessoryMapper.map(accessory, ProductResponseDTO.builder().id(accessory.getProduct()).build()))
                .orElseThrow(() -> new IllegalArgumentException("Accessory not found"));
    }

    @Override
    public List<AccessoryResponse> getAll(UUID managementEntity) {
        // Assuming you want to fetch all accessories for a specific management entity
        List<Accessory> accessories = repository.findAllByManagementEntity(managementEntity);
        Map<UUID, ProductResponseDTO> products;

        // Fetch products for the management entity
        List<ProductResponseDTO> productResponseDTOS = administrationClient.getByManagementEntity(managementEntity);

        // Map the products to a UUID to ProductResponseDTO map
        products = productResponseDTOS.stream()
                .collect(Collectors.toMap(ProductResponseDTO::id, product -> product));

        // Map each accessory to its corresponding product
        return accessories.stream()
                .map(accessory -> {
                    ProductResponseDTO product = products.get(accessory.getProduct());
                    return AccessoryMapper.map(accessory, product);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        Accessory accessory = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Accessory not found"));
        repository.delete(accessory);
    }
}

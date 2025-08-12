package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.AccessoryRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.AccessoryResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Accessory;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.Endorsement;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.mappers.AccessoryMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.EndorsementMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.AccessoryRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.EndorsementRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.services.AccessoryService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository repository;
    private final ProductRepository productRepository;
    private final EndorsementRepository endorsementRepository;

    @Override
    public AccessoryResponse create(AccessoryRequest request, UUID managementEntity) {
        // Résoudre relations
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Endorsement endorsement = endorsementRepository.findByUuid(request.actType())
                .orElseThrow(() -> new IllegalArgumentException("Endorsement not found"));

        // Mapper la requête -> entité (sans relations)
        Accessory entity = AccessoryMapper.map(request);
        entity.setProduct(product);
        entity.setActType(endorsement);            // actType = Endorsement
        entity.setManagementEntity(managementEntity);

        entity = repository.save(entity);

        return AccessoryMapper.map(
                entity,
                ProductMapper.map(product),
                EndorsementMapper.map(endorsement)
        );
    }

    @Override
    public AccessoryResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(a -> AccessoryMapper.map(
                        a,
                        ProductMapper.map(a.getProduct()),
                        EndorsementMapper.map(a.getActType())
                ))
                .orElseThrow(() -> new IllegalArgumentException("Accessory not found"));
    }

    @Override
    public List<AccessoryResponse> getAll(UUID managementEntity) {
        List<Accessory> accessories = repository.findAllByManagementEntity(managementEntity);

        return accessories.stream()
                .map(a -> AccessoryMapper.map(
                        a,
                        ProductMapper.map(a.getProduct()),
                        EndorsementMapper.map(a.getActType())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        Accessory accessory = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Accessory not found"));
        accessory.setDeleted(true);
        repository.save(accessory);
    }

    @Override
    public AccessoryResponse update(UUID uuid, AccessoryRequest request, UUID managementEntity) {
        Accessory accessory = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Accessory not found"));

        if (!accessory.getManagementEntity().equals(managementEntity)) {
            throw new IllegalArgumentException("Accessory does not belong to the specified management entity");
        }

        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Endorsement endorsement = endorsementRepository.findByUuid(request.actType())
                .orElseThrow(() -> new IllegalArgumentException("Endorsement not found"));

        // Maj champs
        accessory.setProduct(product);
        accessory.setActType(endorsement);
        accessory.setAccessoryAmount(request.accessoryAmount());
        accessory.setAccessoryRisk(request.accessoryRisk());
        accessory.setDateEffective(request.dateEffective());
        accessory.setDay(request.day());
        accessory.setHour(request.hour());
        accessory.setMinute(request.minute());

        accessory = repository.save(accessory);

        return AccessoryMapper.map(
                accessory,
                ProductMapper.map(product),
                EndorsementMapper.map(endorsement)
        );
    }
}

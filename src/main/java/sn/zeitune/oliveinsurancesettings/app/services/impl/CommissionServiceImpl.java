package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Commission;
import sn.zeitune.oliveinsurancesettings.app.mappers.CommissionMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CommissionRepository;
import sn.zeitune.oliveinsurancesettings.app.services.CommissionService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommissionServiceImpl implements CommissionService {

    private final CommissionRepository repository;
    private final AdministrationClient administrationClient;

    @Override
    public CommissionResponse create(CommissionRequest request, UUID managementEntity) {
        Commission entity = CommissionMapper.map(request);
        entity.setManagementEntity(managementEntity);

        entity.setProduct(request.productId());
        entity.setPointOfSale(request.salesPointId());
        entity = repository.save(entity);
        return CommissionMapper.map(entity, ProductResponseDTO.builder().id(entity.getProduct()).build());
    }


    @Override
    public CommissionResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(commission -> CommissionMapper.map(commission, ProductResponseDTO.builder().id(commission.getProduct()).build()))
                .orElseThrow(() -> new IllegalArgumentException("Commission not found"));
    }

    @Override
    public List<CommissionResponse> getAll(UUID managementEntity) {
        List<Commission> commissions = repository.findAllByManagementEntity(managementEntity);

        Map<UUID, ProductResponseDTO> products;

        // Fetch products for the management entity
        List<ProductResponseDTO> productResponseDTOS = administrationClient.getByManagementEntity(managementEntity);

        // Map the products to a UUID to ProductResponseDTO map
        products = productResponseDTOS.stream()
                .collect(Collectors.toMap(ProductResponseDTO::id, product -> product));

        // Map each commission to its corresponding product
        return commissions.stream()
                .map(commission -> {
                    ProductResponseDTO product = products.get(commission.getProduct());
                    return CommissionMapper.map(commission, product);
                })
                .collect(Collectors.toList());

    }

    @Override
    public void delete(UUID uuid) {
        Commission commission = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Commission not found"));
        repository.delete(commission);
    }
}

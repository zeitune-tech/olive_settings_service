package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Commission;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.mappers.CommissionMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CommissionRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
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
    private final ProductRepository productRepository;

    private final AdministrationClient administrationClient;

    @Override
    public CommissionResponse create(CommissionRequest request, UUID managementEntity) {
        Commission entity = CommissionMapper.map(request);
        entity.setManagementEntity(managementEntity);
        entity.setPointOfSale(request.salesPointId());

        // Validate that the product exists in the product repository
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        entity.setProduct(product);
        entity = repository.save(entity);

        return CommissionMapper.map(entity, ProductMapper.map(product));
    }


    @Override
    public CommissionResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(commission -> CommissionMapper.map(commission,
                        ProductMapper.map(commission.getProduct()),
                        null)) // Point of sale is not mapped here, can be added if needed
                .orElseThrow(() -> new IllegalArgumentException("Commission not found"));
    }

    @Override
    public List<CommissionResponse> getAll(UUID managementEntity) {
        List<Commission> commissions = repository.findAllByManagementEntity(managementEntity);

        Map<UUID, ProductResponseDTO> products;


        // Map the points of sale to a UUID to ProductResponseDTO map
        Map<UUID, ManagementEntityResponse> pointsOfSale = administrationClient.getManagementEntities(
                commissions.stream()
                        .map(Commission::getPointOfSale)
                        .distinct()
                        .collect(Collectors.toList())
                ).stream()
                .collect(Collectors.toMap(ManagementEntityResponse::id, pointOfSale -> pointOfSale));

        // Map each commission to its corresponding product
        return commissions.stream()
                .map(commission -> {
                    ManagementEntityResponse pointOfSale = pointsOfSale.get(commission.getPointOfSale());
                    return CommissionMapper.map(commission, ProductMapper.map(commission.getProduct()), pointOfSale);
                })
                .collect(Collectors.toList());

    }

    @Override
    public void delete(UUID uuid) {
        Commission commission = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Commission not found"));

        // Soft delete the commission
        commission.setDeleted(true);
        repository.save(commission);
    }

    @Override
    public CommissionResponse update(UUID uuid, CommissionRequest request, UUID managementEntity) {
        Commission commission = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Commission not found"));

        // Validate that the management entity matches
        if (!commission.getManagementEntity().equals(managementEntity)) {
            throw new IllegalArgumentException("Commission does not belong to the specified management entity");
        }

        // Validate that the product exists in the product repository
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        commission.setProduct(product);

        // Update other fields
        commission.setPointOfSale(request.salesPointId());
        commission.setContributionRate(request.contributionRate());
        commission.setDateEffective(request.dateEffective());

        commission = repository.save(commission);
        return CommissionMapper.map(commission, ProductMapper.map(product), null); // Point of sale is not mapped here, can be added if needed
    }
}

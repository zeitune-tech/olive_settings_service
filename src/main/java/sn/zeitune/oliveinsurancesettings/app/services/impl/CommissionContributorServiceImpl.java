package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionContributorResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.Commission;
import sn.zeitune.oliveinsurancesettings.app.entities.CommissionContributor;
import sn.zeitune.oliveinsurancesettings.app.mappers.CommissionContributorMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.CommissionMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CommissionContributorRepository;
import sn.zeitune.oliveinsurancesettings.app.services.CommissionContributorService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommissionContributorServiceImpl implements CommissionContributorService {

    private final CommissionContributorRepository repository;
    private final AdministrationClient administrationClient;

    @Override
    public CommissionContributorResponse create(CommissionContributorRequest request, UUID managementEntity) {
        CommissionContributor entity = CommissionContributorMapper.map(request);
        entity.setManagementEntity(managementEntity);
        entity.setContributor(request.contributorId());
        entity.setProduct(request.productId());
        entity = repository.save(entity);
        return CommissionContributorMapper.map(
                entity,
                ProductResponseDTO.builder().id(entity.getProduct()).build()
        );
    }


    @Override
    public CommissionContributorResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(commission -> CommissionContributorMapper.map(commission, ProductResponseDTO.builder().id(commission.getProduct()).build()))
                .orElseThrow(() -> new IllegalArgumentException("CommissionContributor not found"));
    }

    @Override
    public List<CommissionContributorResponse> getAll(UUID managementEntity) {
        List<CommissionContributor> commissions = repository.findAllByManagementEntity(managementEntity);

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
                    return CommissionContributorMapper.map(commission, product);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        CommissionContributor entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CommissionContributor not found"));
        repository.delete(entity);
    }
}

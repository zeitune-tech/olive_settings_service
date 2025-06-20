package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionContributorResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.CommissionContributor;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.mappers.CommissionContributorMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CommissionContributorRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
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
    private final ProductRepository productRepository;

    @Override
    public CommissionContributorResponse create(CommissionContributorRequest request, UUID managementEntity) {
        CommissionContributor entity = CommissionContributorMapper.map(request);
        entity.setManagementEntity(managementEntity);
        entity.setContributor(request.contributorId());

        // Validate that the product exists in the product repository
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        entity.setProduct(product);
        entity = repository.save(entity);
        return CommissionContributorMapper.map(
                entity,
                ProductMapper.map(product)
        );
    }


    @Override
    public CommissionContributorResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(commission -> CommissionContributorMapper.map(commission,
                        ProductMapper.map(commission.getProduct())))
                .orElseThrow(() -> new IllegalArgumentException("CommissionContributor not found"));
    }

    @Override
    public List<CommissionContributorResponse> getAll(UUID managementEntity) {
        List<CommissionContributor> commissions = repository.findAllByManagementEntity(managementEntity);


        // Map each commission to its corresponding product
        return commissions.stream()
                .map(commission -> CommissionContributorMapper.map(commission,
                        ProductMapper.map(commission.getProduct())))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        CommissionContributor entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CommissionContributor not found"));

        // Soft delete the commission contributor
        entity.setDeleted(true);
        repository.save(entity);
    }

    @Override
    public CommissionContributorResponse update(UUID uuid, CommissionContributorRequest request, UUID managementEntity) {
        CommissionContributor entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CommissionContributor not found"));

        // Update the entity with the new values from the request

        entity.setCommissionBase(request.commissionBase());
        entity.setContributorRate(request.contributorRate());
        entity.setDateEffective(request.dateEffective());
        entity.setUpperEntityContributorRate(request.upperEntityContributorRate());
        entity.setContributor(request.contributorId());

        // Validate that the product exists in the product repository
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        entity.setProduct(product);

        entity = repository.save(entity);
        return CommissionContributorMapper.map(
                entity,
                ProductMapper.map(product)
        );
    }
}

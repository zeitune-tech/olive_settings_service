package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionTaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionTaxResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.CommissionTax;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.mappers.CommissionTaxMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CommissionTaxRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.services.CommissionTaxService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommissionTaxServiceImpl implements CommissionTaxService {

    private final CommissionTaxRepository repository;
    private final ProductRepository productRepository;
    private final AdministrationClient administrationClient;

    @Override
    public CommissionTaxResponse create(CommissionTaxRequest request, UUID managementEntity) {
        CommissionTax entity = CommissionTaxMapper.map(request);
        entity.setManagementEntity(managementEntity);
        // Validate that the product exists in the product repository
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        entity.setProduct(product);
        entity = repository.save(entity);

        return CommissionTaxMapper.map(
                entity,
                ProductMapper.map(product)
        );
    }

    @Override
    public CommissionTaxResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(tax -> CommissionTaxMapper.map(
                        tax,
                        ProductMapper.map(tax.getProduct())
                ))
                .orElseThrow(() -> new IllegalArgumentException("CommissionTax not found"));
    }

    @Override
    public List<CommissionTaxResponse> getAll(UUID managementEntity) {
        List<CommissionTax> taxes = repository.findAllByManagementEntity(managementEntity);


        return taxes.stream()
                .map(tax -> CommissionTaxMapper.map(tax, ProductMapper.map(tax.getProduct())))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        CommissionTax tax = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CommissionTax not found"));

        tax.setDeleted(true);
        repository.save(tax);
    }

    @Override
    public CommissionTaxResponse update(UUID uuid, CommissionTaxRequest request, UUID managementEntity) {
        CommissionTax tax = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CommissionTax not found"));

        // Update the fields of the tax entity
        tax.setRate(request.rate());
        tax.setCommissionTaxType(request.commissionTaxType());
        tax.setDateEffective(request.dateEffective());

        // Validate that the product exists in the product repository
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        tax.setProduct(product);

        tax = repository.save(tax);

        return CommissionTaxMapper.map(tax, ProductMapper.map(product));
    }
}

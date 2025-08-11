package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxExemptionRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxExemptionResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.Tax;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxExemption;
import sn.zeitune.oliveinsurancesettings.app.mappers.BranchMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.TaxMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxExemptionRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxRepository;
import sn.zeitune.oliveinsurancesettings.app.services.TaxExemptionService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TaxExemptionServiceImpl implements TaxExemptionService {

    private final TaxExemptionRepository taxExemptionRepository;
    private final ProductRepository productRepository;
    private final TaxRepository taxRepository;

    @Override
    public TaxExemptionResponse create(TaxExemptionRequest request, UUID managementEntity) {

        List<Tax> taxes = taxRepository.findAllByUuidInAndDeletedFalse(request.taxIds());

        if (taxes.isEmpty()) {
            throw new IllegalArgumentException("No valid taxes found for the provided IDs.");
        }

        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        TaxExemption taxExemption = TaxMapper.map(request, product, new HashSet<>(taxes));
        taxExemption.setManagementEntity(managementEntity);
        taxExemptionRepository.save(taxExemption);

        Set<TaxResponse> taxResponses = new HashSet<>();
        for (Tax tax : taxes) {
            taxResponses.add(TaxMapper.map(tax));
        }

        return TaxMapper.map(taxExemption, ProductMapper.map(product, BranchMapper.map(product.getBranch()), null), taxResponses);

    }

    @Override
    public TaxExemptionResponse update(UUID uuid, TaxExemptionRequest request) {
        TaxExemption taxExemption = taxExemptionRepository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new IllegalArgumentException("TaxExemption not found"));

        List<Tax> taxes = taxRepository.findAllByUuidInAndDeletedFalse(request.taxIds());

        if (taxes.isEmpty()) {
            throw new IllegalArgumentException("No valid taxes found for the provided IDs.");
        }

        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        taxExemption.setTaxes(new HashSet<>(taxes));
        taxExemption.setProduct(product);
        taxExemption.setName(request.name());

        taxExemptionRepository.save(taxExemption);

        Set<TaxResponse> taxResponses = new HashSet<>();
        for (Tax tax : taxes) {
            taxResponses.add(TaxMapper.map(tax));
        }

        return TaxMapper.map(taxExemption, ProductMapper.map(product, BranchMapper.map(product.getBranch()), null), taxResponses);
    }

    @Override
    public TaxExemptionResponse getByUuid(UUID uuid) {
        return taxExemptionRepository.findByUuidAndDeletedFalse(uuid)
                .map(taxExemption -> {
                    Product product = productRepository.findByUuid(taxExemption.getProduct().getUuid())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                    Set<TaxResponse> taxResponses = new HashSet<>();
                    for (Tax tax : taxExemption.getTaxes()) {
                        taxResponses.add(TaxMapper.map(tax));
                    }

                    return TaxMapper.map(taxExemption, ProductMapper.map(product, BranchMapper.map(product.getBranch()), null), taxResponses);
                })
                .orElseThrow(() -> new IllegalArgumentException("TaxExemption not found"));
    }

    @Override
    public List<TaxExemptionResponse> getAllByManagementEntity(UUID managementEntity) {
        return taxExemptionRepository.findAllByManagementEntityAndDeletedFalse(managementEntity)
                .stream()
                .map(taxExemption -> {
                    Product product = productRepository.findByUuid(taxExemption.getProduct().getUuid())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                    Set<TaxResponse> taxResponses = new HashSet<>();
                    for (Tax tax : taxExemption.getTaxes()) {
                        taxResponses.add(TaxMapper.map(tax));
                    }

                    return TaxMapper.map(taxExemption, ProductMapper.map(product, BranchMapper.map(product.getBranch()), null), taxResponses);
                })
                .toList();
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        TaxExemption taxExemption = taxExemptionRepository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new IllegalArgumentException("TaxExemption not found"));

        taxExemption.setDeleted(true);
        taxExemptionRepository.save(taxExemption);
    }
}

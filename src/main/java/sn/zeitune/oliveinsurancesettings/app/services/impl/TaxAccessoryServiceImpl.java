package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxAccessoryResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxAccessory;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxPremium;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxType;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.CoverageMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.TaxMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxAccessoryRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxTypeRepository;
import sn.zeitune.oliveinsurancesettings.app.services.TaxAccessoryService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TaxAccessoryServiceImpl implements TaxAccessoryService {

    private final TaxAccessoryRepository taxRepository;
    private final TaxTypeRepository taxTypeRepository;
    private final ProductRepository productRepository;


    @Override
    public TaxAccessoryResponse create(TaxRequest request, UUID managementEntity) {
        TaxType taxType = taxTypeRepository.findByUuidAndDeletedFalse(request.taxTypeId())
                .orElseThrow(() -> new NotFoundException("TaxType not found"));

        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        TaxAccessory taxAccessory = TaxMapper.map(request, taxType, product);
        taxAccessory.setManagementEntity(managementEntity);
        taxRepository.save(taxAccessory);

        return TaxMapper.map(
                taxAccessory,
                TaxMapper.map(taxType),
                ProductMapper.map(product)
        );
    }

    @Override
    public TaxAccessoryResponse update(UUID uuid, TaxRequest request) {
        TaxAccessory taxAccessory = taxRepository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("TaxAccessory not found"));

        TaxType taxType = taxTypeRepository.findByUuidAndDeletedFalse(request.taxTypeId())
                .orElseThrow(() -> new NotFoundException("TaxType not found"));

        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        taxAccessory.setDateEffective(request.dateEffective());
        taxAccessory.setRate(request.rate());
        taxAccessory.setIsFlatRate(request.isFlatRate());
        taxAccessory.setFlatRateAmount(request.flatRateAmount());
        taxAccessory.setName(request.name());
        taxAccessory.setTaxType(taxType);
        taxAccessory.setProduct(product);

        taxRepository.save(taxAccessory);

        return TaxMapper.map(
                taxAccessory,
                TaxMapper.map(taxType),
                ProductMapper.map(product)
        );
    }

    @Override
    public TaxAccessoryResponse getByUuid(UUID uuid) {
        TaxAccessory taxAccessory = taxRepository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("TaxAccessory not found"));

        TaxType taxType = taxTypeRepository.findByUuidAndDeletedFalse(taxAccessory.getTaxType().getUuid())
                .orElseThrow(() -> new NotFoundException("TaxType not found"));

        Product product = productRepository.findByUuid(taxAccessory.getProduct().getUuid())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return TaxMapper.map(
                taxAccessory,
                TaxMapper.map(taxType),
                ProductMapper.map(product)
        );
    }

    @Override
    public List<TaxAccessoryResponse> getAllByManagementEntity(UUID managementEntity) {
        List<TaxAccessory> taxAccessories = taxRepository.findAllByManagementEntityAndDeletedFalse(managementEntity);
        return taxAccessories.stream()
                .map(taxAccessory -> {
                    TaxType taxType = taxTypeRepository.findByUuidAndDeletedFalse(taxAccessory.getTaxType().getUuid())
                            .orElseThrow(() -> new NotFoundException("TaxType not found"));
                    Product product = productRepository.findByUuid(taxAccessory.getProduct().getUuid())
                            .orElseThrow(() -> new NotFoundException("Product not found"));
                    return TaxMapper.map(
                            taxAccessory,
                            TaxMapper.map(taxType),
                            ProductMapper.map(product)
                    );
                })
                .toList();
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        TaxAccessory taxAccessory = taxRepository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new NotFoundException("TaxAccessory not found"));

        taxAccessory.setDeleted(true);
        taxRepository.save(taxAccessory);
    }
}

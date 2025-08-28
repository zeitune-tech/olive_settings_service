package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxPremiumResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxPremium;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxType;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.CoverageMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.TaxMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxTypeRepository;
import sn.zeitune.oliveinsurancesettings.app.services.TaxPremiumService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TaxPremiumServiceImpl implements TaxPremiumService {

    private final TaxRepository taxRepository;
    private final TaxTypeRepository taxTypeRepository;
    private final CoverageRepository coverageRepository;
    private final ProductRepository productRepository;

    @Override
    public TaxPremiumResponse create(TaxRequest request, UUID managementEntity) {
        TaxType taxType = taxTypeRepository.findByUuid(request.taxTypeId())
                .orElseThrow(() -> new NotFoundException("TaxType not found"));

        Coverage coverage = coverageRepository.findByUuid(request.coverageId())
                .orElse(null);

        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        TaxPremium taxPremium = TaxMapper.map(request, taxType, coverage, product);
        taxPremium.setManagementEntity(managementEntity);
        taxRepository.save(taxPremium);


        return TaxMapper.map(
                taxPremium,
                TaxMapper.map(taxType),
                CoverageMapper.map(coverage, null, null),
                ProductMapper.map(product)
        );
    }

    @Override
    public TaxPremiumResponse update(UUID uuid, TaxRequest request) {
        TaxPremium taxPremium = (TaxPremium) taxRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("TaxPremium not found"));

        TaxType taxType = taxTypeRepository.findByUuid(request.taxTypeId())
                .orElseThrow(() -> new NotFoundException("TaxType not found"));

        Coverage coverage = coverageRepository.findByUuid(request.coverageId())
                .orElseThrow(() -> new NotFoundException("Coverage not found"));

        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        taxPremium.setDateEffective(request.dateEffective());
        taxPremium.setRate(request.isFlatRate() ? 0.0 : request.rate());
        taxPremium.setIsFlatRate(request.isFlatRate());
        taxPremium.setFlatRateAmount(request.isFlatRate() ? request.flatRateAmount() : 0.0);
        taxPremium.setName(request.name());
        taxPremium.setTaxType(taxType);
        taxPremium.setCoverage(coverage);
        taxPremium.setProduct(product);

        taxRepository.save(taxPremium);

        return TaxMapper.map(
                taxPremium,
                TaxMapper.map(taxType),
                CoverageMapper.map(coverage, null, null),
                ProductMapper.map(product)
        );
    }

    @Override
    public TaxPremiumResponse getByUuid(UUID uuid) {
        TaxPremium taxPremium = (TaxPremium) taxRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("TaxPremium not found"));

        TaxType taxType = taxPremium.getTaxType();
        Coverage coverage = taxPremium.getCoverage();
        Product product = taxPremium.getProduct();

        return TaxMapper.map(
                taxPremium,
                TaxMapper.map(taxType),
                CoverageMapper.map(coverage, null, null),
                ProductMapper.map(product)
        );
    }

    @Override
    public List<TaxPremiumResponse> getAllActive(UUID managementEntity) {
        return taxRepository.findAllByManagementEntityAndDeletedIsFalse(managementEntity).stream()
                .filter(t -> t instanceof TaxPremium)
                .map(tax -> {
                    TaxPremium taxPremium = (TaxPremium) tax;
                    TaxType taxType = taxPremium.getTaxType();
                    Coverage coverage = taxPremium.getCoverage();
                    Product product = taxPremium.getProduct();
                    return TaxMapper.map(
                            taxPremium,
                            TaxMapper.map(taxType),
                            CoverageMapper.map(coverage, null, null),
                            ProductMapper.map(product)
                    );
                })
                .toList();
    }

    @Override
    public List<TaxPremiumResponse> getAllIncludeDeleted(UUID managementEntity) {
        return taxRepository.findAllByManagementEntity(managementEntity).stream()
                .filter(t -> t instanceof TaxPremium)
                .map(tax -> {
                    TaxPremium taxPremium = (TaxPremium) tax;
                    TaxType taxType = taxPremium.getTaxType();
                    Coverage coverage = taxPremium.getCoverage();
                    Product product = taxPremium.getProduct();
                    return TaxMapper.map(
                            taxPremium,
                            TaxMapper.map(taxType),
                            CoverageMapper.map(coverage, null, null),
                            ProductMapper.map(product)
                    );
                })
                .toList();
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        TaxPremium taxPremium = (TaxPremium) taxRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("TaxPremium not found"));

        taxPremium.setDeleted(true);
        taxRepository.save(taxPremium);
    }
}

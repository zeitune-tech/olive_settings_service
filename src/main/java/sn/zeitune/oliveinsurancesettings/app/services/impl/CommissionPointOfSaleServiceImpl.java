package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionPointOfSaleRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionPointOfSaleResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionPointOfSale;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionPointOfSaleAccessory;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionPointOfSalePremium;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.CommissionMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.CoverageMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.*;
import sn.zeitune.oliveinsurancesettings.app.services.CommissionPointOfSaleService;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommissionPointOfSaleServiceImpl implements CommissionPointOfSaleService {

    private final CommissionPointOfSaleRepository commissionPointOfSaleRepository;
    private final CommissionPointOfSalePrimeRepository commissionPointOfSalePrimeRepository;
    private final CommissionPointOfSaleAccessoryRepository commissionPointOfSaleAccessoryRepository;
    private final CoverageRepository coverageRepository;
    private final ProductRepository productRepository;

    @Override
    public CommissionPointOfSaleResponse create(CommissionPointOfSaleRequest request, UUID managementEntity) {
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (request.calculationBase() == CalculationBase.PRIME) {

            Coverage coverage = null;
            if (request.coverageId() != null) {
                coverage = coverageRepository.findByUuid(request.coverageId())
                        .orElseThrow( () -> new NotFoundException("Coverage not found"));
            }

            CommissionPointOfSalePremium premium = CommissionMapper.map(request, coverage, product);
            premium.setManagementEntity(managementEntity);
            premium.setDeleted(false);

            premium = commissionPointOfSaleRepository.save(premium);

            return CommissionMapper.map(
                    premium,
                    ProductMapper.map(product),
                    CoverageMapper.map(coverage, null, null),
                    null
            );
        } else if (request.calculationBase() == CalculationBase.ACCESSORY) {

            CommissionPointOfSaleAccessory accessory = CommissionMapper.map(request, product);
            accessory.setManagementEntity(managementEntity);
            accessory.setDeleted(false);

            accessory = commissionPointOfSaleRepository.save(accessory);

            return CommissionMapper.map(
                    accessory,
                    ProductMapper.map(product),
                    null,
                    null
            );
        } else {
            throw new IllegalArgumentException("Invalid commission base type");
        }
    }

    @Override
    public CommissionPointOfSaleResponse update(UUID uuid, CommissionPointOfSaleRequest request, UUID managementEntity) {
        CommissionPointOfSale commission = commissionPointOfSaleRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("CommissionPointOfSale not found"));

        if (commission instanceof CommissionPointOfSalePremium) {
            if (request.calculationBase() != CalculationBase.PRIME) {
                throw new IllegalArgumentException("Cannot update to non-PRIME commission base for a premium point of sale");
            }
            Coverage coverage = coverageRepository.findByUuid(request.coverageId())
                    .orElseThrow(() -> new NotFoundException("Coverage not found"));
            ((CommissionPointOfSalePremium) commission).setCoverage(coverage);
        } else if (commission instanceof CommissionPointOfSaleAccessory) {
            if (request.calculationBase() != CalculationBase.ACCESSORY) {
                throw new IllegalArgumentException("Cannot update to non-ACCESSORY commission base for an accessory point of sale");
            }
        } else {
            throw new IllegalArgumentException("Invalid commission point of sale type");
        }

        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        commission.setManagementEntity(managementEntity);
        commission.setDeleted(false);

        commissionPointOfSaleRepository.save(commission);

        return CommissionMapper.map(
                commission,
                ProductMapper.map(product),
                commission instanceof CommissionPointOfSalePremium ?
                        CoverageMapper.map(((CommissionPointOfSalePremium) commission).getCoverage(), null, null) : null,
                null
        );
    }

    @Override
    public List<CommissionPointOfSaleResponse> getAllPrimes(UUID managementEntity) {
        return commissionPointOfSalePrimeRepository
                .findAllByManagementEntity(managementEntity).stream()
                .map(commission -> CommissionMapper.map(
                        commission,
                        ProductMapper.map(commission.getProduct(), null, null),
                        CoverageMapper.map(commission.getCoverage(), null, null),
                        null
                ))
                .toList();
    }

    @Override
    public List<CommissionPointOfSaleResponse> getAllAccessories(UUID managementEntity) {
        return commissionPointOfSaleAccessoryRepository
                .findAllByManagementEntity(managementEntity).stream()
                .map(commission -> CommissionMapper.map(
                        commission,
                        ProductMapper.map(commission.getProduct(), null, null),
                        null,
                        null
                ))
                .toList();
    }

    @Override
    public CommissionPointOfSaleResponse getByUuid(UUID uuid) {
        CommissionPointOfSale commission = commissionPointOfSaleRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("CommissionPointOfSale not found"));

        Product product = commission.getProduct();

        return CommissionMapper.map(
                commission,
                ProductMapper.map(product, null, null),
                commission instanceof CommissionPointOfSalePremium ?
                        CoverageMapper.map(((CommissionPointOfSalePremium) commission).getCoverage(), null, null) : null,
                null
        );
    }

    @Override
    public List<CommissionPointOfSaleResponse> getAll(UUID managementEntity) {
        return commissionPointOfSaleRepository
                .findAllByManagementEntity(managementEntity).stream()
                .map(commission -> CommissionMapper.map(
                        commission,
                        ProductMapper.map(commission.getProduct(), null, null),
                        commission instanceof CommissionPointOfSalePremium ?
                                CoverageMapper.map(((CommissionPointOfSalePremium) commission).getCoverage(), null, null) : null,
                        null
                ))
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        CommissionPointOfSale commission = commissionPointOfSaleRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("CommissionPointOfSale not found"));

        commission.setDeleted(true);
        commissionPointOfSaleRepository.save(commission);
    }
}

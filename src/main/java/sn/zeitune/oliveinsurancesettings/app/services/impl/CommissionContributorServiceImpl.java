package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionContributorRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionContributorResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionContributor;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionContributorAccessory;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionContributorPremium;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.CommissionMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.CoverageMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CommissionContributorRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.services.CommissionContributorService;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommissionContributorServiceImpl implements CommissionContributorService {

    private final CommissionContributorRepository commissionContributorRepository;
    private final CoverageRepository coverageRepository;
    private final ProductRepository productRepository;

    @Override
    public CommissionContributorResponse create(CommissionContributorRequest request, UUID managementEntity) {
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (request.commissionBase() == CalculationBase.PRIME) {

            Coverage coverage = null;
            if (request.coverageId() != null) {
                // If the commission base is PRIME, coverage must be provided
                coverage = coverageRepository.findByUuid(request.coverageId())
                        .orElse(null);
            } else {
                throw new IllegalArgumentException("Coverage ID must be provided for PRIME commission base");
            }

            CommissionContributorPremium premium = CommissionMapper.map(request, coverage, product);
            premium.setManagementEntity(managementEntity);
            premium.setDeleted(false);

            premium = commissionContributorRepository.save(premium);
            return CommissionMapper.map(
                    premium,
                    ProductMapper.map(product),
                    coverage != null ? CoverageMapper.map(
                            coverage,
                            null,
                            null
                    ) : null,
                    null
            );
        } else if (request.commissionBase() == CalculationBase.ACCESSORY) {

            CommissionContributorAccessory accessory = CommissionMapper.map(request, product);
            accessory.setManagementEntity(managementEntity);
            accessory.setDeleted(false);

            accessory = commissionContributorRepository.save(accessory);
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
    public CommissionContributorResponse update(UUID uuid, CommissionContributorRequest request, UUID managementEntity) {
        CommissionContributor commission = commissionContributorRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("CommissionContributor not found"));

        if (commission instanceof CommissionContributorPremium) {
            if (request.commissionBase() != CalculationBase.PRIME) {
                throw new IllegalArgumentException("Cannot update to non-PRIME commission base for a premium contributor");
            }
            Coverage coverage = coverageRepository.findByUuid(request.coverageId())
                    .orElseThrow(() -> new NotFoundException("Coverage not found"));
            ((CommissionContributorPremium) commission).setCoverage(coverage);
        } else if (commission instanceof CommissionContributorAccessory) {
            if (request.commissionBase() != CalculationBase.ACCESSORY) {
                throw new IllegalArgumentException("Cannot update to non-ACCESSORY commission base for an accessory contributor");
            }
        } else {
            throw new IllegalArgumentException("Invalid commission contributor type");
        }

        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        commission.setProduct(product);
        commission.setDeleted(false);

        commissionContributorRepository.save(commission);

        return CommissionMapper.map(
                commission,
                ProductMapper.map(product),
                commission instanceof CommissionContributorPremium ?
                        CoverageMapper.map(((CommissionContributorPremium) commission).getCoverage(), null, null) : null,
                null
        );
    }

    @Override
    public List<CommissionContributorResponse> getAllAccessories(UUID managementEntity) {
        return commissionContributorRepository
                .findAllByManagementEntity(managementEntity).stream()
                .filter(commission -> commission instanceof CommissionContributorAccessory)
                .map(commission -> CommissionMapper.map(
                        commission,
                        ProductMapper.map(commission.getProduct(), null, null),
                        null,
                        null
                ))
                .toList();
    }


    @Override
    public List<CommissionContributorResponse> getAllPrimes(UUID managementEntity) {
        return commissionContributorRepository
                .findAllByManagementEntity(managementEntity).stream()
                .filter(commission -> commission instanceof CommissionContributorPremium)
                .map(commission -> CommissionMapper.map(
                        commission,
                        ProductMapper.map(commission.getProduct(), null, null),
                        CoverageMapper.map(commission.getCoverage(), null, null),
                        null
                ))
                .toList();
    }

    @Override
    public CommissionContributorResponse getByUuid(UUID uuid) {
        CommissionContributor commission = commissionContributorRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("CommissionContributor not found"));

        Product product = commission.getProduct();

        return CommissionMapper.map(
                commission,
                ProductMapper.map(product, null, null),
                commission instanceof CommissionContributorPremium ?
                        CoverageMapper.map(((CommissionContributorPremium) commission).getCoverage(), null, null) : null,
                null
        );
    }

    @Override
    public List<CommissionContributorResponse> getAll(UUID managementEntity) {
        return commissionContributorRepository
                .findAllByManagementEntity(managementEntity).stream()
                .map(commission -> CommissionMapper.map(
                        commission,
                        ProductMapper.map(commission.getProduct(), null, null),
                        commission instanceof CommissionContributorPremium ?
                                CoverageMapper.map(((CommissionContributorPremium) commission).getCoverage(), null, null) : null,
                        null
                ))
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        CommissionContributor commission = commissionContributorRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("CommissionContributor not found"));

        commission.setDeleted(true);
        commissionContributorRepository.save(commission);
    }
}

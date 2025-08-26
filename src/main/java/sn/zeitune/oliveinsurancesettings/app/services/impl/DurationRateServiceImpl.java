package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.DurationRateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.DurationRateResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageDuration;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageDurationRate;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.mappers.CoverageDurationMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.DurationRateMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CoverageDurationRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.DurationRateRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.services.DurationRateService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DurationRateServiceImpl implements DurationRateService {

    private final DurationRateRepository repository;
    private final CoverageDurationRepository coverageDurationRepository;
    private final AdministrationClient administrationClient;
    private final ProductRepository productRepository;

    @Override
    public DurationRateResponse create(DurationRateRequest request, UUID managementEntity) {
        CoverageDurationRate entity = DurationRateMapper.map(request);
        entity.setManagementEntity(managementEntity);
        // Validate that the product exists in the product repository
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        entity.setProduct(product);

        CoverageDuration coverageDuration = coverageDurationRepository.findByUuid(request.durationId())
                .orElseThrow(() -> new IllegalArgumentException("CoverageDuration not found"));
        entity.setDuration(coverageDuration);

        entity = repository.save(entity);
        return DurationRateMapper.map(entity, ProductMapper.map(product));
    }


    @Override
    public DurationRateResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map( duration -> DurationRateMapper.map(duration,
                        ProductMapper.map(duration.getProduct())))
                .orElseThrow(() -> new IllegalArgumentException("DurationRate not found"));
    }

    @Override
    public List<DurationRateResponse> getAll(UUID managementEntity) {

        // Fetch all duration rates for the management entity
        List<CoverageDurationRate> coverageDurationRates = repository.findAllByManagementEntity(managementEntity);

        // Map each duration rate to its corresponding product
        return coverageDurationRates.stream()
                .map(coverageDurationRate -> DurationRateMapper.map(coverageDurationRate,
                        ProductMapper.map(coverageDurationRate.getProduct())))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        CoverageDurationRate entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("DurationRate not found"));

        entity.setDeleted(true);
        repository.save(entity);
    }

    @Override
    public DurationRateResponse update(UUID uuid, DurationRateRequest request, UUID managementEntity) {
        CoverageDurationRate entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("DurationRate not found"));
        // TODO: verify managementEntity privileges

        entity.setRate(request.rate());
        entity.setDateEffective(request.dateEffective());

        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        entity.setProduct(product);

        CoverageDuration duration = coverageDurationRepository.findByUuid(request.durationId())
                .orElseThrow(() -> new IllegalArgumentException("CoverageDuration not found"));
        entity.setDuration(duration);

        return DurationRateMapper.map(repository.save(entity), ProductMapper.map(product));
    }
}

package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.EndorsementRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.EndorsementResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.Endorsement;
import sn.zeitune.oliveinsurancesettings.app.mappers.EndorsementMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.EndorsementRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.services.EndorsementService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EndorsementServiceImpl implements EndorsementService {

    private final EndorsementRepository endorsementRepository;
    private final ProductRepository productRepository;

    @Override
    public EndorsementResponse create(EndorsementRequest request, UUID managementEntity) {
        var endorsement = EndorsementMapper.map(request);
        endorsement.setManagementEntity(managementEntity);
        endorsementRepository.save(endorsement);
        return EndorsementMapper.map(endorsement);
    }

    @Override
    public EndorsementResponse findByUuid(UUID uuid) {
        Endorsement endorsement = endorsementRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Endorsement not found with UUID: " + uuid));
        return EndorsementMapper.map(endorsement);
    }

    @Override
    public List<EndorsementResponse> findAllByManagementEntity(UUID managementEntity) {
        List<Endorsement> endorsements = endorsementRepository.findAllByManagementEntity(managementEntity);
        return endorsements.stream()
                .map(EndorsementMapper::map)
                .toList();
    }

    @Override
    public EndorsementResponse addProductToEndorsement(UUID endorsementUuid, UUID productUuid) {
        Endorsement endorsement = endorsementRepository.findByUuid(endorsementUuid)
                .orElseThrow(() -> new IllegalArgumentException("Endorsement not found with UUID: " + endorsementUuid));

        var product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with UUID: " + productUuid));

        // Ensure the product is not already in the endorsement
        if (endorsement.getProduct().contains(product)) {
            throw new IllegalArgumentException("Product already exists in the endorsement");
        }
        endorsement.getProduct().add(product);

        endorsementRepository.save(endorsement);
        return EndorsementMapper.map(endorsement);
    }

    @Override
    public EndorsementResponse removeProductFromEndorsement(UUID endorsementUuid, UUID productUuid) {
        Endorsement endorsement = endorsementRepository.findByUuid(endorsementUuid)
                .orElseThrow(() -> new IllegalArgumentException("Endorsement not found with UUID: " + endorsementUuid));

        var product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with UUID: " + productUuid));

        // Ensure the product is in the endorsement
        if (!endorsement.getProduct().contains(product)) {
            throw new IllegalArgumentException("Product does not exist in the endorsement");
        }
        endorsement.getProduct().remove(product);

        endorsementRepository.save(endorsement);
        return EndorsementMapper.map(endorsement);
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        endorsementRepository.deleteByUuid(uuid);
    }
}

package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.EndorsementRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.EndorsementResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.Endorsement;
import sn.zeitune.oliveinsurancesettings.app.mappers.EndorsementMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.EndorsementRepository;
import sn.zeitune.oliveinsurancesettings.app.services.EndorsementService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EndorsementServiceImpl implements EndorsementService {

    private final EndorsementRepository endorsementRepository;

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
    public void deleteByUuid(UUID uuid) {
        endorsementRepository.deleteByUuid(uuid);
    }

    @Autowired
    private final ProductRepository productRepository;

    @Override
    public void assignProducts(UUID endorsementId, List<UUID> productIds) {
        Endorsement endorsement = endorsementRepository.findByUuid(endorsementId)
                .orElseThrow(() -> new IllegalArgumentException("Endorsement not found with UUID: " + endorsementId));

        List<Product> products = productRepository.findAllById(productIds);

        endorsement.setProducts(products); // méthode à avoir dans ton entité Endorsement
        endorsementRepository.save(endorsement);
    }

}

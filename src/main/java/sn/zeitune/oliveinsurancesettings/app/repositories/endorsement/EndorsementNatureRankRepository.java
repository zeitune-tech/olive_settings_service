// sn/zeitune/oliveinsurancesettings/app/repositories/endorsement/EndorsementNatureRankRepository.java
package sn.zeitune.oliveinsurancesettings.app.repositories.endorsement;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.EndorsementNatureRank;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EndorsementNatureRankRepository extends JpaRepository<EndorsementNatureRank, Long> {
    List<EndorsementNatureRank> findAllByManagementEntity(UUID managementEntity);
    Optional<EndorsementNatureRank> findByManagementEntityAndNature(UUID managementEntity, Enum<?> nature);
    void deleteByManagementEntity(UUID managementEntity);
}

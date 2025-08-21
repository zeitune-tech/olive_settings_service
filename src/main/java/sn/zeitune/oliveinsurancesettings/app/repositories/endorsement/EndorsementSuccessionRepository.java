// sn/zeitune/oliveinsurancesettings/app/repositories/endorsement/EndorsementSuccessionRepository.java
package sn.zeitune.oliveinsurancesettings.app.repositories.endorsement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.EndorsementSuccession;

import java.util.List;
import java.util.UUID;

public interface EndorsementSuccessionRepository extends JpaRepository<EndorsementSuccession, Long> {
    List<EndorsementSuccession> findAllByManagementEntity(UUID managementEntity);

    // Option A: dérivée (garde-la si tu veux)
    void deleteByManagementEntity(UUID managementEntity);

    // Option B: explicite (recommandée si tu as encore des doublons résiduels)
    @Modifying
    @Transactional
    @Query("DELETE FROM EndorsementSuccession s WHERE s.managementEntity = :tenant")
    void hardDeleteByManagementEntity(@Param("tenant") UUID tenant);}

package sn.zeitune.oliveinsurancesettings.app.repositories.endorsement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.Endorsement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EndorsementRepository extends JpaRepository<Endorsement, Long>, JpaSpecificationExecutor<Endorsement> {
    Optional<Endorsement> findByUuid(UUID uuid);

    List<Endorsement> findAllByManagementEntity(UUID managementEntity);

    void deleteByUuid(UUID uuid);
}

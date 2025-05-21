package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.Commission;
import sn.zeitune.oliveinsurancesettings.app.entities.CommissionContributor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionContributorRepository extends JpaRepository<CommissionContributor, Long>, JpaSpecificationExecutor<CommissionContributor> {
    Optional<CommissionContributor> findByUuid(UUID uuid);

    List<CommissionContributor> findAllByManagementEntity(UUID managementEntity);
}

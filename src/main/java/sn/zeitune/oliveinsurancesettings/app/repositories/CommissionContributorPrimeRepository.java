package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionContributorPremium;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionContributorPrimeRepository extends JpaRepository<CommissionContributorPremium, Long>,
        JpaSpecificationExecutor<CommissionContributorPremium> {

    Optional<CommissionContributorPremium> findByUuid(UUID uuid);

    List<CommissionContributorPremium> findAllByManagementEntity(UUID managementEntity);
}

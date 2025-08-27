package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionContributorAccessory;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionContributorPremium;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionContributorAccessoryRepository extends JpaRepository<CommissionContributorAccessory, Long>,
        JpaSpecificationExecutor<CommissionContributorAccessory> {

    Optional<CommissionContributorAccessory> findByUuid(UUID uuid);

    List<CommissionContributorAccessory> findAllByManagementEntity(UUID managementEntity);
}

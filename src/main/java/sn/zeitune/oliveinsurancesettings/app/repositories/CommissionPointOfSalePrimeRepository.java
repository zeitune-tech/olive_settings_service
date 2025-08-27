package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionPointOfSale;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionPointOfSalePremium;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionPointOfSalePrimeRepository extends JpaRepository<CommissionPointOfSalePremium, Long>,
            JpaSpecificationExecutor<CommissionPointOfSalePremium> {

        Optional<CommissionPointOfSalePremium> findByUuid(UUID uuid);

        List<CommissionPointOfSalePremium> findAllByManagementEntity(UUID managementEntity);
}

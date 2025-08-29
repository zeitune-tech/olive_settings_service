package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionPointOfSaleResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionPointOfSale;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionPointOfSalePremium;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionPointOfSaleRepository extends JpaRepository<CommissionPointOfSale, Long>,
        JpaSpecificationExecutor<CommissionPointOfSale> {

    Optional<CommissionPointOfSale> findByUuid(UUID uuid);

    List<CommissionPointOfSale> findAllByManagementEntity(UUID managementEntity);
    List<CommissionPointOfSale> findAllByManagementEntityAndDeletedIsFalse(UUID managementEntity);

}

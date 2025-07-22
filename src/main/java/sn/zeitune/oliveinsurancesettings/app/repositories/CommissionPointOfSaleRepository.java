package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionPointOfSale;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionPointOfSaleRepository extends JpaRepository<CommissionPointOfSale, Long>,
        JpaSpecificationExecutor<CommissionPointOfSale> {

    Optional<CommissionPointOfSale> findByUuidAndDeletedFalse(UUID uuid);

    List<CommissionPointOfSale> findAllByManagementEntityAndDeletedFalse(UUID managementEntity);
}

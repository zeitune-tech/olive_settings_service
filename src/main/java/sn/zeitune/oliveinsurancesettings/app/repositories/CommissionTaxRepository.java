package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.CommissionTax;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionTaxRepository extends JpaRepository<CommissionTax, Long>, JpaSpecificationExecutor<CommissionTax> {

    Optional<CommissionTax> findByUuid(UUID uuid);

    List<CommissionTax> findAllByProductAndManagementEntity(UUID product, UUID managementEntity);

    List<CommissionTax> findAllByManagementEntity(UUID managementEntity);
}

package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.CommissionTax;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionTaxRepository extends JpaRepository<CommissionTax, Long>, JpaSpecificationExecutor<CommissionTax> {

    @Query("SELECT ct FROM commissions_taxe ct WHERE ct.uuid = :uuid AND ct.deleted = false")
    Optional<CommissionTax> findByUuid(UUID uuid);

    @Query("SELECT ct FROM commissions_taxe ct WHERE  ct.managementEntity = :managementEntity AND ct.deleted = false")
    List<CommissionTax> findAllByManagementEntity(UUID managementEntity);
}

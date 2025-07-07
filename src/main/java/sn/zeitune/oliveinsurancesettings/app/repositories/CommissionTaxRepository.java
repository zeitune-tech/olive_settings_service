package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.TaxCommissionsPointOfSale;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionTaxRepository extends JpaRepository<TaxCommissionsPointOfSale, Long>, JpaSpecificationExecutor<TaxCommissionsPointOfSale> {

    @Query("SELECT ct FROM commissions_taxe ct WHERE ct.uuid = :uuid AND ct.deleted = false")
    Optional<TaxCommissionsPointOfSale> findByUuid(UUID uuid);

    @Query("SELECT ct FROM commissions_taxe ct WHERE  ct.managementEntity = :managementEntity AND ct.deleted = false")
    List<TaxCommissionsPointOfSale> findAllByManagementEntity(UUID managementEntity);
}

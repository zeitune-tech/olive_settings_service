package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseTax;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseTaxRepository extends JpaRepository<BaseTax, Long>, JpaSpecificationExecutor<BaseTax> {

    @Query("SELECT a FROM bases_taxe a WHERE a.uuid = :uuid AND a.deleted = false")
    Optional<BaseTax> findByUuid(UUID uuid);

    @Query("SELECT a FROM bases_taxe a WHERE a.managementEntity = :managementEntity AND a.deleted = false")
    List<BaseTax> findAllByManagementEntity(UUID managementEntity);
}

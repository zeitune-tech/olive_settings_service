package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.Commission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionRepository extends JpaRepository<Commission, Long>, JpaSpecificationExecutor<Commission> {

    @Query("SELECT c FROM commissions c WHERE c.uuid = :uuid AND c.deleted = false")
    Optional<Commission> findByUuid(UUID uuid);

    @Query("SELECT c FROM commissions c WHERE c.managementEntity = :managementEntity AND c.deleted = false")
    List<Commission> findAllByManagementEntity(UUID managementEntity);
}

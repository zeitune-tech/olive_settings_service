package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.Commission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionRepository extends JpaRepository<Commission, Long>, JpaSpecificationExecutor<Commission> {
    Optional<Commission> findByUuid(UUID uuid);

    List<Commission> findAllByManagementEntity(UUID managementEntity);
}

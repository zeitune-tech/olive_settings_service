package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.Coverage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CoverageRepository extends JpaRepository<Coverage, Long>, JpaSpecificationExecutor<Coverage> {
    Optional<Coverage> findByUuid(UUID uuid);
    List<Coverage> findAllByProductAndManagementEntity(UUID productUuid, UUID managementEntity);
}


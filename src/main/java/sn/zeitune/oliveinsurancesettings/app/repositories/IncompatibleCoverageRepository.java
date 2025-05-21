package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.IncompatibleCoverage;

import java.util.Optional;
import java.util.UUID;

public interface IncompatibleCoverageRepository extends JpaRepository<IncompatibleCoverage, Long>, JpaSpecificationExecutor<IncompatibleCoverage> {
    Optional<IncompatibleCoverage> findByUuid(UUID uuid);

    Page<IncompatibleCoverage> findAllByManagementEntity(UUID managementEntity, Pageable pageable);
}


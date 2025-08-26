package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.IncompatibleCoverage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncompatibleCoverageRepository extends JpaRepository<IncompatibleCoverage, Long>, JpaSpecificationExecutor<IncompatibleCoverage> {

    Optional<IncompatibleCoverage> findByUuid(UUID uuid);
    Optional<IncompatibleCoverage> findByCoverageAndIncompatibleCoverageAndManagementEntityAndDeletedIsFalse(
            Coverage coverage,
            Coverage incompatibleCoverage,
            UUID managementEntity
    );

    Page<IncompatibleCoverage> findAllByManagementEntityAndDeletedIsFalse(UUID managementEntity, Pageable pageable);

}


package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.IncompatibleCoverage;

import java.util.Optional;
import java.util.UUID;

public interface IncompatibleCoverageRepository extends JpaRepository<IncompatibleCoverage, Long>, JpaSpecificationExecutor<IncompatibleCoverage> {

    @Query("SELECT ic FROM garanties_incompatibles ic WHERE ic.uuid = :uuid AND ic.deleted = false")
    Optional<IncompatibleCoverage> findByUuid(UUID uuid);

    @Query("SELECT ic FROM garanties_incompatibles ic WHERE ic.managementEntity = :managementEntity AND ic.deleted = false")
    Page<IncompatibleCoverage> findAllByManagementEntity(UUID managementEntity, Pageable pageable);
}


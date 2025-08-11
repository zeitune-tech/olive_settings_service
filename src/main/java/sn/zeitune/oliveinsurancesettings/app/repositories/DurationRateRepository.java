package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageDurationRate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DurationRateRepository extends JpaRepository<CoverageDurationRate, Long>, JpaSpecificationExecutor<CoverageDurationRate> {

    Optional<CoverageDurationRate> findByUuid(UUID uuid);

    List<CoverageDurationRate> findAllByManagementEntity(UUID managementEntity);
}

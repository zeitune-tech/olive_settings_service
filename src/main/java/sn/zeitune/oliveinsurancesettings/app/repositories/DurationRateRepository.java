package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.DurationRate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DurationRateRepository extends JpaRepository<DurationRate, Long>, JpaSpecificationExecutor<DurationRate> {
    Optional<DurationRate> findByUuid(UUID uuid);
}

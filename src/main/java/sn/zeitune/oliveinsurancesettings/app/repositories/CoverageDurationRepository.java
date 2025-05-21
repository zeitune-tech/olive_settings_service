package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.oliveinsurancesettings.app.entities.CoverageDuration;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CoverageDurationRepository extends JpaRepository<CoverageDuration, Long> {

    Optional<CoverageDuration> findByUuid(UUID uuid);

    List<CoverageDuration> findAllByManagementEntity(UUID managementEntity);
}
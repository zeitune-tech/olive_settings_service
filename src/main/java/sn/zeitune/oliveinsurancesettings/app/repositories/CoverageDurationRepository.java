package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageDuration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CoverageDurationRepository extends JpaRepository<CoverageDuration, Long> {

    Optional<CoverageDuration> findByUuidAndDeletedIsFalse(UUID uuid);
    Optional<CoverageDuration> findByUuid(UUID uuid);

    List<CoverageDuration> findAllByManagementEntityAndDeletedIsFalse(UUID managementEntity);

    List<CoverageDuration> findAllByManagementEntity(UUID managementEntity);


}
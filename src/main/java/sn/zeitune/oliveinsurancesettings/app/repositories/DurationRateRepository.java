package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageDurationRate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DurationRateRepository extends JpaRepository<CoverageDurationRate, Long>, JpaSpecificationExecutor<CoverageDurationRate> {

    @Query("SELECT dr FROM taux_duree dr WHERE dr.uuid = :uuid AND dr.deleted = false")
    Optional<CoverageDurationRate> findByUuid(UUID uuid);

    @Query("SELECT dr FROM taux_duree dr WHERE dr.managementEntity = :managementEntity AND dr.deleted = false")
    List<CoverageDurationRate> findAllByManagementEntity(UUID managementEntity);
}

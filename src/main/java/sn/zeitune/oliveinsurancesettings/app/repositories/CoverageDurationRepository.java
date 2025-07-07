package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageDuration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CoverageDurationRepository extends JpaRepository<CoverageDuration, Long> {

    @Query("SELECT cd FROM durees_de_couverture cd WHERE cd.uuid = :uuid AND cd.deleted = false")
    Optional<CoverageDuration> findByUuid(UUID uuid);

    @Query("SELECT cd FROM durees_de_couverture cd WHERE cd.managementEntity = :managementEntity AND cd.deleted = false")
    List<CoverageDuration> findAllByManagementEntity(UUID managementEntity);
}
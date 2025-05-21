package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.TaxRegime;

import java.util.Optional;
import java.util.UUID;

public interface TaxRegimeRepository extends JpaRepository<TaxRegime, Long>, JpaSpecificationExecutor<TaxRegime> {
    Optional<TaxRegime> findByUuid(UUID uuid);
}

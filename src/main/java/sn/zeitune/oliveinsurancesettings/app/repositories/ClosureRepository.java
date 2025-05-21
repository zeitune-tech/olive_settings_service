package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.oliveinsurancesettings.app.entities.Closure;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClosureRepository extends JpaRepository<Closure, Long> {
    Optional<Closure> findByUuid(UUID uuid);

    List<Closure> findAllByManagementEntity(UUID managementEntity);
}
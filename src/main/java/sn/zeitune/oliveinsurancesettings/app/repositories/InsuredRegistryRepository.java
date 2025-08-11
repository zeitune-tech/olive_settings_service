package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.InsuredRegistry;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InsuredRegistryRepository extends JpaRepository<InsuredRegistry, Long> {

    Optional<InsuredRegistry> findByUuid(UUID uuid);

    Page<InsuredRegistry> findAllByManagementEntity(UUID managementEntity, Pageable pageable);
}

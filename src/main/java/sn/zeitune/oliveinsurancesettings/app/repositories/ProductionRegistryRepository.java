package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.zeitune.oliveinsurancesettings.app.entities.ProductionRegistry;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductionRegistryRepository extends JpaRepository<ProductionRegistry, Long> {

    Optional<ProductionRegistry> findByUuid(UUID uuid);

    Page<ProductionRegistry> findAllByManagementEntity(UUID managementEntity, Pageable pageable);
    List<ProductionRegistry> findAllByManagementEntity(UUID managementEntity);
}

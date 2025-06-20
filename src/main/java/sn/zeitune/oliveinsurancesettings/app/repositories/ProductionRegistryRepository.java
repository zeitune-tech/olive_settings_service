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

    @Query("SELECT pr FROM registres_de_production pr WHERE pr.uuid = :uuid AND pr.deleted = false")
    Optional<ProductionRegistry> findByUuid(UUID uuid);

    @Query("SELECT pr FROM registres_de_production pr WHERE pr.managementEntity = :managementEntity AND pr.deleted = false")
    Page<ProductionRegistry> findAllByManagementEntity(UUID managementEntity, Pageable pageable);
    @Query("SELECT pr FROM registres_de_production pr WHERE pr.managementEntity = :managementEntity AND pr.deleted = false")
    List<ProductionRegistry> findAllByManagementEntity(UUID managementEntity);
}

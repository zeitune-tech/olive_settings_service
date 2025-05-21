package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.Accessory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccessoryRepository extends JpaRepository<Accessory, Long>, JpaSpecificationExecutor<Accessory> {
    Optional<Accessory> findByUuid(UUID uuid);
    List<Accessory> findAllByProductAndManagementEntity(UUID productId, UUID managementEntityId);

    List<Accessory> findAllByManagementEntity(UUID managementEntity);
}

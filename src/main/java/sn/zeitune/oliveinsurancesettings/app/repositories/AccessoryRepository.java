package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.Accessory;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccessoryRepository extends JpaRepository<Accessory, Long>, JpaSpecificationExecutor<Accessory> {


    @Query("SELECT a FROM accessoires a WHERE a.uuid = :uuid AND a.deleted = false")
    Optional<Accessory> findByUuid(UUID uuid);

    @Query("SELECT a FROM accessoires a WHERE a.deleted = false")
    List<Accessory> findAllByManagementEntity(UUID managementEntity);
}

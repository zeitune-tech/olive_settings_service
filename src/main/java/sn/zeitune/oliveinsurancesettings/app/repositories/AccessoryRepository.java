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


    Optional<Accessory> findByUuid(UUID uuid);

    List<Accessory> findAllByManagementEntity(UUID managementEntity);
}

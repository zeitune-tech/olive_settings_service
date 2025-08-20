package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Brand;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleModelRepository extends JpaRepository<Model, Long>, JpaSpecificationExecutor<Model> {

    Optional<Model> findByUuid(UUID uuid);

    Page<Model> findAllByBrand(Pageable pageable, Brand brand);

    List<Model> findAllByBrand(Brand brand);

    Optional<Model> findByBrandAndName(Brand brand, String upperCase);
    Optional<Model> findByBrandAndUuid(Brand brand, UUID uuid);

    Page<Model> findByNameContainingIgnoreCaseAndBrand(String name, Brand brand, Pageable pageable);

    Page<Model> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Brand;

import java.util.Optional;
import java.util.UUID;

public interface VehicleBrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

    Optional<Brand> findByUuid(UUID uuid);

    Optional<Brand> findByName(String name);

    boolean existsByName(String brandName);

    Page<Brand> findByNameContainingIgnoreCase(String name, Pageable pageable);

}

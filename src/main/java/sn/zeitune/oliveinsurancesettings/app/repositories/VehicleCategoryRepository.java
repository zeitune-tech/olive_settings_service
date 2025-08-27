package sn.zeitune.oliveinsurancesettings.app.repositories;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.VehicleCategory;

import java.util.Optional;
import java.util.UUID;

public interface VehicleCategoryRepository extends JpaRepository<VehicleCategory, Long>, JpaSpecificationExecutor<VehicleCategory> {
    Optional<VehicleCategory> findByUuid(UUID uuid);

    boolean existsByNameIgnoreCase(@NotNull @NotEmpty String name);
}

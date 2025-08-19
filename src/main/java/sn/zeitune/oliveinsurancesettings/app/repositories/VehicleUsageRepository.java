package sn.zeitune.oliveinsurancesettings.app.repositories;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.zeitune.oliveinsurancesettings.app.entities.VehicleUsage;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleUsageRepository extends JpaRepository<VehicleUsage, UUID> {
//    Page<VehicleUsage> findByActiveTrue(Pageable pageable);
//    Optional<VehicleUsage> findByIdAndActiveTrue(UUID id);
    Optional<VehicleUsage> findByUuid(UUID uuid);

    boolean existsByName(@NotNull @NotEmpty String name);
}

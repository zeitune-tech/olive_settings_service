package sn.zeitune.oliveinsurancesettings.app.repositories;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageReference;

import java.util.*;

@Repository
public interface CoverageReferenceRepository  extends JpaRepository<CoverageReference, Long> {

    Optional<CoverageReference> findByUuid(UUID uuid);

    CoverageReference findByDesignationAndManagementEntity(@NotBlank(message = "Designation must not be blank") String designation, UUID managementEntity);

    List<CoverageReference> findAllByManagementEntity(UUID managementEntity);

    Set<CoverageReference> findAllByUuidInAndDeletedIsFalse(Set<UUID> coverages);
}
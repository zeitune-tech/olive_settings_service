package sn.zeitune.oliveinsurancesettings.app.repositories;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.zeitune.oliveinsurancesettings.app.entities.CoverageReference;

import java.util.*;

@Repository
public interface CoverageReferenceRepository  extends JpaRepository<CoverageReference, Long> {

    @Query("SELECT cr FROM references_garantie cr WHERE cr.uuid = :uuid AND cr.deleted = false")
    Optional<CoverageReference> findByUuid(UUID uuid);

    @Query("SELECT cr FROM references_garantie cr WHERE cr.designation = :designation AND cr.managementEntity = :managementEntity AND cr.deleted = false")
    CoverageReference findByDesignationAndManagementEntity(@NotBlank(message = "Designation must not be blank") String designation, UUID managementEntity);

    @Query("SELECT cr FROM references_garantie cr WHERE cr.managementEntity = :managementEntity AND cr.deleted = false")
    List<CoverageReference> findAllByManagementEntity(UUID managementEntity);

    Set<CoverageReference> findAllByUuidInAndDeletedIsFalse(Set<UUID> coverages);
}
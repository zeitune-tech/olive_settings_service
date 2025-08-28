package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.Tax;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaxRepository extends JpaRepository<Tax, Long>, JpaSpecificationExecutor<Tax> {

    Optional<Tax> findByUuid(UUID uuid);

    List<Tax> findAllByManagementEntity(UUID managementEntity);
    List<Tax> findAllByManagementEntityAndDeletedIsFalse(UUID managementEntity);

    List<Tax> findAllByUuidInAndDeletedFalse(List<UUID> uuids);
}

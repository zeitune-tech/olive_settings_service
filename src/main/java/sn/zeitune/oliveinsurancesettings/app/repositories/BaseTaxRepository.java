package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseTax;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseTaxRepository extends JpaRepository<BaseTax, Long>, JpaSpecificationExecutor<BaseTax> {
    Optional<BaseTax> findByUuid(UUID uuid);

    List<BaseTax> findAllByManagementEntity(UUID managementEntity);
}

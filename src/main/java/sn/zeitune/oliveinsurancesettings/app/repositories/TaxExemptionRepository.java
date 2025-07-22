package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxExemption;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaxExemptionRepository extends JpaRepository<TaxExemption, Long>, JpaSpecificationExecutor<TaxExemption> {

    Optional<TaxExemption> findByUuidAndDeletedFalse(UUID uuid);

    List<TaxExemption> findAllByManagementEntityAndDeletedFalse(UUID managementEntity);
}

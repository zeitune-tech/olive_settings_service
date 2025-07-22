package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxPremium;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaxPremiumRepository extends JpaRepository<TaxPremium, Long>, JpaSpecificationExecutor<TaxPremium> {

    Optional<TaxPremium> findByUuidAndDeletedFalse(UUID uuid);

    List<TaxPremium> findAllByManagementEntityAndDeletedFalse(UUID managementEntity);
}

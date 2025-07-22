package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.TaxCommissionsContributor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaxCommissionsContributorRepository extends JpaRepository<TaxCommissionsContributor, Long> {

    Optional<TaxCommissionsContributor> findByUuidAndDeletedFalse(UUID uuid);

    List<TaxCommissionsContributor> findAllByManagementEntityAndDeletedFalse(UUID managementEntity);
}

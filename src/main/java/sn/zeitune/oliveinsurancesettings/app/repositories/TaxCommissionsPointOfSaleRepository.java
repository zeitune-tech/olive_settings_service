package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.TaxCommissionsContributor;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.TaxCommissionsPointOfSale;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaxCommissionsPointOfSaleRepository  extends JpaRepository<TaxCommissionsPointOfSale, Long> {


    Optional<TaxCommissionsPointOfSale> findByUuidAndDeletedFalse(UUID uuid);

    List<TaxCommissionsPointOfSale> findAllByManagementEntityAndDeletedFalse(UUID managementEntity);
}

package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.comission.CommissionContributorPremium;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionContributorRepository extends JpaRepository<CommissionContributorPremium, Long>, JpaSpecificationExecutor<CommissionContributorPremium> {

    @Query("SELECT cc FROM commissions_apporteur cc WHERE cc.uuid = :uuid AND cc.deleted = false")
    Optional<CommissionContributorPremium> findByUuid(UUID uuid);

    @Query("SELECT cc FROM commissions_apporteur cc WHERE cc.managementEntity = :managementEntity AND cc.deleted = false")
    List<CommissionContributorPremium> findAllByManagementEntity(UUID managementEntity);
}

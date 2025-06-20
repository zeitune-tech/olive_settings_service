package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.Commission;
import sn.zeitune.oliveinsurancesettings.app.entities.CommissionContributor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommissionContributorRepository extends JpaRepository<CommissionContributor, Long>, JpaSpecificationExecutor<CommissionContributor> {

    @Query("SELECT cc FROM commissions_apporteur cc WHERE cc.uuid = :uuid AND cc.deleted = false")
    Optional<CommissionContributor> findByUuid(UUID uuid);

    @Query("SELECT cc FROM commissions_apporteur cc WHERE cc.managementEntity = :managementEntity AND cc.deleted = false")
    List<CommissionContributor> findAllByManagementEntity(UUID managementEntity);
}

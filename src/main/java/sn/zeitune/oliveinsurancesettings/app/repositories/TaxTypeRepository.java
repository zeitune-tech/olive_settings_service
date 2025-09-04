package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaxTypeRepository extends JpaRepository<TaxType, Long>, JpaSpecificationExecutor<TaxType> {

    Optional<TaxType> findByUuid(UUID uuid);

    List<TaxType> findAllByDeletedFalse();

    boolean existsByNameAndDeletedFalse(String name);

    List<TaxType> findAllByManagementEntityAndDeletedFalse(UUID managementEntity);

    List<TaxType> findAllByUuidInAndDeletedFalse(List<UUID> uuids);

}

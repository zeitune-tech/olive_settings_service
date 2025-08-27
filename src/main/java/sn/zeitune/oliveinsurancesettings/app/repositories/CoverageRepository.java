package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageReference;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CoverageRepository extends JpaRepository<Coverage, Long>, JpaSpecificationExecutor<Coverage> {
    
    Optional<Coverage> findByUuid(UUID uuid);
    List<Coverage> findAllByProductAndManagementEntity(Product product, UUID managementEntity);
    
    List<Coverage> findAllByManagementEntity(UUID managementEntity);

    Set<Coverage> findAllByProduct(Product product);

    void deleteAllByProduct(Product product);

    Optional<Coverage> findByProductAndCoverageReference(Product product, CoverageReference reference);

    boolean existsByProductAndCoverageReference_Uuid(Product product, UUID coverage);
}


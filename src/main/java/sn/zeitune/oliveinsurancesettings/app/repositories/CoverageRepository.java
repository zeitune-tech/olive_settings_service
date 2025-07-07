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
    
    @Query("SELECT c FROM produits_garanties c WHERE c.uuid = :uuid AND c.deleted = false")
    Optional<Coverage> findByUuid(UUID uuid);
    @Query("SELECT c FROM produits_garanties c WHERE c.product = :product AND c.managementEntity = :managementEntity AND c.deleted = false")
    List<Coverage> findAllByProductAndManagementEntity(Product product, UUID managementEntity);
    
    @Query("SELECT c FROM produits_garanties c WHERE c.managementEntity = :managementEntity AND c.deleted = false")
    List<Coverage> findAllByManagementEntity(UUID managementEntity);

    @Query("SELECT c FROM produits_garanties c WHERE c.product = :product AND c.deleted = false")
    Set<Coverage> findAllByProduct(Product product);

    void deleteAllByProduct(Product product);

    Optional<Coverage> findByProductAndCoverageReference(Product product, CoverageReference reference);
}


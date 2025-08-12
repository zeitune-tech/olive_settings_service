package sn.zeitune.oliveinsurancesettings.app.repositories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.Branch;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByUuid(UUID uuid);

    List<Product> findAllByOwner(UUID owner);
    Page<Product> findAllByOwner(UUID owner, Pageable pageable);

    List<Product> findAllByOwnerAndBranch(UUID owner, Branch branch);

    boolean existsByNameAndBranchAndOwner(String name, Branch branch, UUID owner);

    List<Product> findAllByUuidIn(List<UUID> uuids);
}

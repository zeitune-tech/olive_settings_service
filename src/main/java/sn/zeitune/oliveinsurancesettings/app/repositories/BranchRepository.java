package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.Branch;
import sn.zeitune.oliveinsurancesettings.app.entities.Category;

import java.util.Optional;
import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByUuid(UUID uuid);

    Optional<Branch> findByName(String name);

    boolean existsByCategory(Category category);
}

package sn.zeitune.oliveinsurancesettings.app.repositories;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.zeitune.oliveinsurancesettings.app.entities.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM classes c WHERE c.uuid = :uuid AND c.deleted = false")
    Optional<Category> findByUuid(UUID uuid);

    boolean existsByName(@NotBlank(message = "Name is mandatory") String name);

    @Query("SELECT c FROM classes c WHERE c.name = :name AND c.deleted = false")
    Optional<Category> findByName(String name);
}

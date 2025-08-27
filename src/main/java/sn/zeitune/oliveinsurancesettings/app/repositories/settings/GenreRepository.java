package sn.zeitune.oliveinsurancesettings.app.repositories.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Genre;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, Long>, JpaSpecificationExecutor<Genre> {
    Optional<Genre> findByUuid(UUID uuid);
    boolean existsByCodeAndDeletedFalse(String code);
    boolean existsByLibelleIgnoreCaseAndDeletedFalse(String libelle);
    boolean existsByCodeAndDeletedFalseAndIdNot(String code, Long id);
    boolean existsByLibelleIgnoreCaseAndDeletedFalseAndIdNot(String libelle, Long id);
}


package sn.zeitune.oliveinsurancesettings.app.repositories.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Genre;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Usage;

import java.util.Optional;
import java.util.UUID;

public interface UsageRepository extends JpaRepository<Usage, Long>, JpaSpecificationExecutor<Usage> {
    Optional<Usage> findByUuid(UUID uuid);

    boolean existsByGenreAndCodeAndDeletedFalse(Genre genre, String code);
    boolean existsByGenreAndLibelleIgnoreCaseAndDeletedFalse(Genre genre, String libelle);
    boolean existsByGenreAndCodeAndDeletedFalseAndIdNot(Genre genre, String code, Long id);
    boolean existsByGenreAndLibelleIgnoreCaseAndDeletedFalseAndIdNot(Genre genre, String libelle, Long id);
}


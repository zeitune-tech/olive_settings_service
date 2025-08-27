package sn.zeitune.oliveinsurancesettings.app.repositories.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sn.zeitune.oliveinsurancesettings.app.entities.settings.Profession;

import java.util.Optional;
import java.util.UUID;

public interface ProfessionRepository extends JpaRepository<Profession, Long>, JpaSpecificationExecutor<Profession> {
    Optional<Profession> findByUuid(UUID uuid);
    boolean existsByCodeAndDeletedFalse(String code);
    boolean existsByLibelleIgnoreCaseAndDeletedFalse(String libelle);
    boolean existsByCodeAndDeletedFalseAndIdNot(String code, Long id);
    boolean existsByLibelleIgnoreCaseAndDeletedFalseAndIdNot(String libelle, Long id);
}


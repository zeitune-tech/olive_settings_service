package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.EndorsementSuccession;
import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;

import java.util.List;

public interface EndorsementSuccessionRepository extends JpaRepository<EndorsementSuccession, Long> {
    List<EndorsementSuccession> findAllByFromNature(NatureEndorsement fromNature);
    void deleteAllByFromNature(NatureEndorsement fromNature);
}

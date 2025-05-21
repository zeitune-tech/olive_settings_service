package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionTaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionTaxResponse;

import java.util.List;
import java.util.UUID;

public interface CommissionTaxService {

    /**
     * Créer une nouvelle CommissionTax
     * @param request les données de création
     * @param managementEntity identifiant de l'entité de gestion
     * @return l'entité créée
     */
    CommissionTaxResponse create(CommissionTaxRequest request, UUID managementEntity);

    /**
     * Récupère une CommissionTax par son UUID
     * @param uuid identifiant unique
     * @return la réponse correspondante
     */
    CommissionTaxResponse getByUuid(UUID uuid);

    /**
     * Liste toutes les CommissionTax d'une entité de gestion
     * @param managementEntity identifiant de l'entité
     * @return liste des taxes
     */
    List<CommissionTaxResponse> getAll(UUID managementEntity);

    /**
     * Supprime une CommissionTax par UUID
     * @param uuid identifiant unique
     */
    void delete(UUID uuid);
}

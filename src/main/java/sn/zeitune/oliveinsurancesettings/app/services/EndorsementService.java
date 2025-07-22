package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.EndorsementRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.EndorsementResponse;

import java.util.List;
import java.util.UUID;

public interface EndorsementService {

    /**
     * Creates a new endorsement.
     *
     * @param request the endorsement request containing the details of the endorsement to be created
     * @return the created endorsement response
     */
    EndorsementResponse create(EndorsementRequest request, UUID managementEntity);

    /**
     * Retrieves an endorsement by its UUID.
     *
     * @param uuid the UUID of the endorsement to retrieve
     * @return the endorsement response if found, otherwise null
     */
    EndorsementResponse findByUuid(UUID uuid);

    /**
     * Retrieves all endorsements for a specific management entity.
     *
     * @param managementEntity the UUID of the management entity
     * @return a list of endorsement responses associated with the management entity
     */
    List<EndorsementResponse> findAllByManagementEntity(UUID managementEntity);

    /**
     * Deletes an endorsement by its UUID.
     *
     * @param uuid the UUID of the endorsement to delete
     */
    void deleteByUuid(UUID uuid);
}

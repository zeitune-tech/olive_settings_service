package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.BranchRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.BranchResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BranchService {

    void init();

    BranchResponseDTO create(BranchRequestDTO dto);
    BranchResponseDTO update(UUID uuid, BranchRequestDTO dto);
    void delete(UUID uuid);
    BranchResponseDTO getByUuid(UUID uuid);
    List<BranchResponseDTO> getAll();

    BranchResponseDTO getByName(String name);
}

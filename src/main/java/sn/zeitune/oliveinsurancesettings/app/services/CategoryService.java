package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CategoryRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CategoryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponseDTO create(CategoryRequestDTO dto);
    CategoryResponseDTO update(UUID uuid, CategoryRequestDTO dto);
    void delete(UUID uuid);
    CategoryResponseDTO getByUuid(UUID uuid);
    List<CategoryResponseDTO> getAll();
}

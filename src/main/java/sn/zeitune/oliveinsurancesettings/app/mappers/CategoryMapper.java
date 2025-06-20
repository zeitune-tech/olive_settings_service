package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CategoryRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CategoryResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.Category;

public class CategoryMapper {

    public static Category map(CategoryRequestDTO dto, Category category) {
        category.setName(dto.name());
        category.setDescription(dto.description());
        return category;
    }

    public static CategoryResponseDTO map(Category category) {
        return new CategoryResponseDTO(
                category.getUuid(),
                category.getName(),
                category.getDescription()
        );
    }
}

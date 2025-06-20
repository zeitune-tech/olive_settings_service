package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.BranchRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.BranchResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.Branch;
import sn.zeitune.oliveinsurancesettings.app.entities.Category;
import sn.zeitune.oliveinsurancesettings.app.mappers.CategoryMapper;

public class BranchMapper {

    public static Branch map(BranchRequestDTO dto, Category category, Branch branch) {
        branch.setName(dto.name());
        branch.setDescription(dto.description());
        branch.setCategory(category);
        return branch;
    }

    public static BranchResponseDTO map(Branch branch) {
        return new BranchResponseDTO(
                branch.getUuid(),
                branch.getName(),
                branch.getDescription(),
                CategoryMapper.map(branch.getCategory())
        );
    }
}

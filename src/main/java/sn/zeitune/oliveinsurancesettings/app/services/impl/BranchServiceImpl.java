package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.BranchRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.BranchResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.Branch;
import sn.zeitune.oliveinsurancesettings.app.entities.Category;
import sn.zeitune.oliveinsurancesettings.app.exceptions.NotFoundException;
import sn.zeitune.oliveinsurancesettings.app.mappers.BranchMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.BranchRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.CategoryRepository;
import sn.zeitune.oliveinsurancesettings.app.services.BranchService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void init() {

        Category category = categoryRepository.findByName("Assurance par répartition")
                .orElseGet(() -> {

                    Category newCategory = new Category();
                    newCategory.setName("Assurance par répartition");
                    newCategory.setDescription("Assurance");

                    categoryRepository.save(newCategory);

                    return newCategory;
                });

        if (branchRepository.findByName("Automobile").isEmpty()) {
            Branch branch = new Branch();
            branch.setName("Automobile");
            branch.setDescription("Assurance automobile");
            branch.setCategory(category);
            branchRepository.save(branch);

            System.out.println("Branch 'Automobile' created successfully.");
        } else {
            System.out.println("Branch 'Automobile' already exists.");
        }
    }

    @Override
    public BranchResponseDTO create(BranchRequestDTO dto) {
        Category category = categoryRepository.findByUuid(dto.categoryUuid())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Branch branch = BranchMapper.map(dto, category, new Branch());
        return BranchMapper.map(branchRepository.save(branch));
    }

    @Override
    public BranchResponseDTO update(UUID uuid, BranchRequestDTO dto) {
        Branch branch = branchRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Branch not found"));

        Category category = categoryRepository.findByUuid(dto.categoryUuid())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        BranchMapper.map(dto, category, branch);
        return BranchMapper.map(branchRepository.save(branch));
    }

    @Override
    public void delete(UUID uuid) {
        Branch branch = branchRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Branch not found"));

        // Soft delete the branch
        branch.setDeleted(true);
        branchRepository.save(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public BranchResponseDTO getByUuid(UUID uuid) {
        Branch branch = branchRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Branch not found"));
        return BranchMapper.map(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponseDTO> getAll() {
        return branchRepository.findAll().stream()
                .map(BranchMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public BranchResponseDTO getByName(String name) {
        return branchRepository.findByName(name)
                .map(BranchMapper::map)
                .orElseThrow(() -> new NotFoundException("Branch not found"));
    }
}

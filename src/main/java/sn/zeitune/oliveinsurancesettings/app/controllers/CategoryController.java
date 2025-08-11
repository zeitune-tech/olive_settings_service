package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CategoryRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CategoryResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.services.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable UUID uuid, @RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.update(uuid, dto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        categoryService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CategoryResponseDTO> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(categoryService.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }
}

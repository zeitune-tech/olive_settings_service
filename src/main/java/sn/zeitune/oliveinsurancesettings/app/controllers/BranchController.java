package sn.zeitune.oliveinsurancesettings.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.BranchRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.BranchResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.services.BranchService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchResponseDTO> create(@RequestBody BranchRequestDTO dto) {
        return ResponseEntity.ok(branchService.create(dto));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<BranchResponseDTO> update(@PathVariable UUID uuid, @RequestBody BranchRequestDTO dto) {
        return ResponseEntity.ok(branchService.update(uuid, dto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        branchService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BranchResponseDTO> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(branchService.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<BranchResponseDTO>> getAll() {
        return ResponseEntity.ok(branchService.getAll());
    }
}

package sn.zeitune.oliveinsurancesettings.app.controllers.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProfessionCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProfessionPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProfessionUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.PageResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProfessionResponse;
import sn.zeitune.oliveinsurancesettings.app.services.settings.ProfessionService;

import java.util.UUID;

@RestController
@RequestMapping("/app/admin/professions")
@RequiredArgsConstructor
public class ProfessionAdminController {

    private final ProfessionService service;

    @PostMapping
    public ResponseEntity<ProfessionResponse> create(@Valid @RequestBody ProfessionCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProfessionResponse> getOne(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProfessionResponse>> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(required = false, defaultValue = "false") Boolean includeDeleted,
            @PageableDefault(size = 10, sort = "libelle") Pageable pageable
    ) {
        Page<ProfessionResponse> page = service.listAdmin(q, actif, includeDeleted, pageable);
        return ResponseEntity.ok(new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        ));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ProfessionResponse> update(@PathVariable UUID uuid, @Valid @RequestBody ProfessionUpdateRequest request) {
        return ResponseEntity.ok(service.update(uuid, request));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<ProfessionResponse> patch(@PathVariable UUID uuid, @Valid @RequestBody ProfessionPatchRequest request) {
        return ResponseEntity.ok(service.patch(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}


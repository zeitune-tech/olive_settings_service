package sn.zeitune.oliveinsurancesettings.app.controllers.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ActivityCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ActivityPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ActivityUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ActivityResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.PageResponse;
import sn.zeitune.oliveinsurancesettings.app.services.settings.ActivityService;

import java.util.UUID;

@RestController
@RequestMapping("/admin/settings/activites")
@RequiredArgsConstructor
public class ActivityAdminController {

    private final ActivityService service;

    @PostMapping
    public ResponseEntity<ActivityResponse> create(@Valid @RequestBody ActivityCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ActivityResponse> getOne(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ActivityResponse>> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(required = false, defaultValue = "false") Boolean includeDeleted,
            @PageableDefault(size = 10, sort = "libelle") Pageable pageable
    ) {
        Page<ActivityResponse> page = service.listAdmin(q, actif, includeDeleted, pageable);
        return ResponseEntity.ok(new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        ));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ActivityResponse> update(@PathVariable UUID uuid, @Valid @RequestBody ActivityUpdateRequest request) {
        return ResponseEntity.ok(service.update(uuid, request));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<ActivityResponse> patch(@PathVariable UUID uuid, @Valid @RequestBody ActivityPatchRequest request) {
        return ResponseEntity.ok(service.patch(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}


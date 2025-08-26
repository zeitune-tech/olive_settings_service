package sn.zeitune.oliveinsurancesettings.app.controllers.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.QualityCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.QualityPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.QualityUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.PageResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.QualityResponse;
import sn.zeitune.oliveinsurancesettings.app.services.settings.QualityService;

import java.util.UUID;

@RestController
@RequestMapping("/admin/settings/qualites")
@RequiredArgsConstructor
public class QualityAdminController {

    private final QualityService service;

    @PostMapping
    public ResponseEntity<QualityResponse> create(@Valid @RequestBody QualityCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<QualityResponse> getOne(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<PageResponse<QualityResponse>> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(required = false, defaultValue = "false") Boolean includeDeleted,
            @PageableDefault(size = 10, sort = "libelle") Pageable pageable
    ) {
        Page<QualityResponse> page = service.listAdmin(q, actif, includeDeleted, pageable);
        return ResponseEntity.ok(new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        ));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<QualityResponse> update(@PathVariable UUID uuid, @Valid @RequestBody QualityUpdateRequest request) {
        return ResponseEntity.ok(service.update(uuid, request));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<QualityResponse> patch(@PathVariable UUID uuid, @Valid @RequestBody QualityPatchRequest request) {
        return ResponseEntity.ok(service.patch(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}


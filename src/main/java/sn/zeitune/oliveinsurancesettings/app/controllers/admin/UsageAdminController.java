package sn.zeitune.oliveinsurancesettings.app.controllers.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.UsageCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.UsagePatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.UsageUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.PageResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.UsageResponse;
import sn.zeitune.oliveinsurancesettings.app.services.settings.UsageService;

import java.util.UUID;

@RestController
@RequestMapping("/app/admin/usages")
@RequiredArgsConstructor
public class UsageAdminController {

    private final UsageService service;

    @PostMapping
    public ResponseEntity<UsageResponse> create(@Valid @RequestBody UsageCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UsageResponse> getOne(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<PageResponse<UsageResponse>> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) UUID genre,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(required = false, defaultValue = "false") Boolean includeDeleted,
            @PageableDefault(size = 10, sort = "libelle") Pageable pageable
    ) {
        Page<UsageResponse> page = service.listAdmin(q, genre, actif, includeDeleted, pageable);
        return ResponseEntity.ok(new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        ));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UsageResponse> update(@PathVariable UUID uuid, @Valid @RequestBody UsageUpdateRequest request) {
        return ResponseEntity.ok(service.update(uuid, request));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<UsageResponse> patch(@PathVariable UUID uuid, @Valid @RequestBody UsagePatchRequest request) {
        return ResponseEntity.ok(service.patch(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}


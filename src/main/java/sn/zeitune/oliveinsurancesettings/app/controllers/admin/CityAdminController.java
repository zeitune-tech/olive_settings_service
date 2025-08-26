package sn.zeitune.oliveinsurancesettings.app.controllers.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CityCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CityPatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CityUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CityResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.PageResponse;
import sn.zeitune.oliveinsurancesettings.app.services.settings.CityService;

import java.util.UUID;

@RestController
@RequestMapping("/app/admin/villes")
@RequiredArgsConstructor
public class CityAdminController {

    private final CityService service;

    @PostMapping
    public ResponseEntity<CityResponse> create(@Valid @RequestBody CityCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CityResponse> getOne(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<PageResponse<CityResponse>> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(required = false, defaultValue = "false") Boolean includeDeleted,
            @PageableDefault(size = 10, sort = "libelle") Pageable pageable
    ) {
        Page<CityResponse> page = service.listAdmin(q, actif, includeDeleted, pageable);
        return ResponseEntity.ok(new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        ));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CityResponse> update(@PathVariable UUID uuid, @Valid @RequestBody CityUpdateRequest request) {
        return ResponseEntity.ok(service.update(uuid, request));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<CityResponse> patch(@PathVariable UUID uuid, @Valid @RequestBody CityPatchRequest request) {
        return ResponseEntity.ok(service.patch(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}


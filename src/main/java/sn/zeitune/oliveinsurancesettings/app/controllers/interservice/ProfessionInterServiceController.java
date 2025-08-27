package sn.zeitune.oliveinsurancesettings.app.controllers.interservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.PageResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProfessionResponse;
import sn.zeitune.oliveinsurancesettings.app.services.settings.ProfessionService;

@RestController
@RequestMapping("/inter-services/settings/professions")
@RequiredArgsConstructor
public class ProfessionInterServiceController {

    private final ProfessionService service;

    @GetMapping
    public ResponseEntity<PageResponse<ProfessionResponse>> list(
            @RequestParam(required = false) String q,
            @PageableDefault(size = 10, sort = "libelle") Pageable pageable
    ) {
        Page<ProfessionResponse> page = service.listInterService(q, pageable);
        return ResponseEntity.ok(new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        ));
    }
}


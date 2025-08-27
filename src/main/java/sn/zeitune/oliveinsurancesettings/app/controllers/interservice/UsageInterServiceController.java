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
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.UsageResponse;
import sn.zeitune.oliveinsurancesettings.app.services.settings.UsageService;

import java.util.UUID;

@RestController
@RequestMapping("/inter-services/settings/usages")
@RequiredArgsConstructor
public class UsageInterServiceController {

    private final UsageService service;

    @GetMapping
    public ResponseEntity<PageResponse<UsageResponse>> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) UUID genre,
            @PageableDefault(size = 10, sort = "libelle") Pageable pageable
    ) {
        Page<UsageResponse> page = service.listInterService(q, genre, pageable);
        return ResponseEntity.ok(new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        ));
    }
}


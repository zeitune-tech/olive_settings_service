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
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.GenreResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.PageResponse;
import sn.zeitune.oliveinsurancesettings.app.services.settings.GenreService;

@RestController
@RequestMapping("/inter-services/settings/genres")
@RequiredArgsConstructor
public class GenreInterServiceController {

    private final GenreService service;

    @GetMapping
    public ResponseEntity<PageResponse<GenreResponse>> list(
            @RequestParam(required = false) String q,
            @PageableDefault(size = 10, sort = "libelle") Pageable pageable
    ) {
        Page<GenreResponse> page = service.listInterService(q, pageable);
        return ResponseEntity.ok(new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        ));
    }
}


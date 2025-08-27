package sn.zeitune.oliveinsurancesettings.app.services.settings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.GenreCreateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.GenrePatchRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.GenreUpdateRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.GenreResponse;

import java.util.UUID;

public interface GenreService {
    GenreResponse create(GenreCreateRequest request);
    GenreResponse getByUuid(UUID uuid);
    Page<GenreResponse> listAdmin(String q, Boolean actif, Boolean includeDeleted, Pageable pageable);
    GenreResponse update(UUID uuid, GenreUpdateRequest request);
    GenreResponse patch(UUID uuid, GenrePatchRequest request);
    void delete(UUID uuid);
    Page<GenreResponse> listInterService(String q, Pageable pageable);
}


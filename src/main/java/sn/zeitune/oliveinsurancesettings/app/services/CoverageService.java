package sn.zeitune.oliveinsurancesettings.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.CoveragesRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CoverageResponse;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;

import java.util.List;
import java.util.UUID;

public interface CoverageService {

    CoverageResponse getByUuid(UUID uuid);
    List<CoverageResponse> getAll();
    CoverageResponse update(UUID uuid, CoverageRequest request);
    Page<CoverageResponse> search(
            String nature,
            String designation,
            Boolean isFree,
            Boolean isFixed,
            CalculationMode calculationMode,
            UUID productUuid,
            UUID managementEntityUuid,
            Pageable pageable
    );
    void delete(UUID uuid);
    List<CoverageResponse> getByProductUuid(UUID productUuid, UUID managementEntity);
    List<CoverageResponse> createCoverages(UUID managementEntity, CoveragesRequest request);
}

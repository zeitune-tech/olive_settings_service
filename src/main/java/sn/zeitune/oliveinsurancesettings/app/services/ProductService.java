package sn.zeitune.oliveinsurancesettings.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductCoveragesUpdate;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductUpdate;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(ProductRequestDTO dto, UUID ownerUuid);
    ProductResponse updateProduct(UUID uuid, ProductUpdate dto);
    ProductResponse addCoverageToProduct(UUID productUuid, ProductCoveragesUpdate productCoverages);
    ProductResponse removeCoverageFromProduct(UUID productUuid, ProductCoveragesUpdate productCoverages);

    void sharePublicProductWithCompanies(UUID productUuid, List<UUID> companyUuids);
    ProductResponse getByUuid(UUID uuid);
    List<ProductResponse> getAll();

    List<ProductResponse> getByManagementEntityUuid(UUID uuid);

    Page<ProductResponse> search(
            String name,
            String branchUuid,
            Integer minRisk,
            Integer maxRisk,
            Boolean fleet,
            Boolean hasReduction,
            Pageable pageable,
            UUID ownerUuid
    );

    List<ProductResponse> getByManagementEntityUuids(List<UUID> uuids);


    void deleteProduct(UUID uuid);
}

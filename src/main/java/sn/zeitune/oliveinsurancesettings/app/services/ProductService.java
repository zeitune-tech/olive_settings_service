package sn.zeitune.oliveinsurancesettings.app.services;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductCoveragesUpdate;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductUpdate;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO dto, UUID ownerUuid);
    ProductResponseDTO updateProduct(UUID uuid, ProductUpdate dto);
    ProductResponseDTO addCoverageToProduct(UUID productUuid, ProductCoveragesUpdate productCoverages);
    ProductResponseDTO removeCoverageFromProduct(UUID productUuid, ProductCoveragesUpdate productCoverages);

    void sharePublicProductWithCompanies(UUID productUuid, List<UUID> companyUuids);
    ProductResponseDTO getByUuid(UUID uuid);
    List<ProductResponseDTO> getAll();

    List<ProductResponseDTO> getByManagementEntityUuid(UUID uuid);

    Page<ProductResponseDTO> search(
            String name,
            String branchUuid,
            Integer minRisk,
            Integer maxRisk,
            Boolean fleet,
            Boolean hasReduction,
            Pageable pageable,
            UUID ownerUuid
    );

    List<ProductResponseDTO> getByManagementEntityUuids(List<UUID> uuids);


    void deleteProduct(UUID uuid);
}

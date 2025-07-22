package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ManagementEntityResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.ProductRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.BranchResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.ProductResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.entities.product.PublicProduct;

import java.util.List;
import java.util.UUID;

public class ProductMapper {

    public static Product map(
            ProductRequestDTO dto,
            UUID branch,
            UUID owner,
            Product product
    ) {
        product.setOwner(owner);
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setMinRisk(dto.minRisk());
        product.setMaxRisk(dto.maxRisk());
        product.setMinimumGuaranteeNumber(dto.minimumGuaranteeNumber());
        product.setFleet(dto.fleet());
        product.setHasReduction(dto.hasReduction());
        return product;
    }

    public static ProductResponse map(Product product) {


        List<UUID> sharedWith;
        if (product instanceof PublicProduct) {
            sharedWith = ((PublicProduct) product).getSharedWithCompanies()
                    .stream()
                    .toList();
        } else {
            sharedWith = List.of();
        }

        return ProductResponse.builder()
                .id(product.getUuid())
                .name(product.getName())
                .description(product.getDescription())
                .branch(null)
                .owner(ManagementEntityResponse.builder()
                        .id(product.getOwner())
                        .build())
                .minRisk(product.getMinRisk())
                .maxRisk(product.getMaxRisk())
                .minimumGuaranteeNumber(product.getMinimumGuaranteeNumber())
                .fleet(product.getFleet())
                .hasReduction(product.isHasReduction())
                .visibility(product.getVisibility())
                .sharedWith(sharedWith)
                .build();
    }

    public static ProductResponse map(Product product, BranchResponseDTO branch, ManagementEntityResponse managementEntityResponse) {
        return ProductResponse.builder()
                .id(product.getUuid())
                .name(product.getName())
                .description(product.getDescription())
                .branch(branch)
                .owner(managementEntityResponse)
                .minRisk(product.getMinRisk())
                .maxRisk(product.getMaxRisk())
                .minimumGuaranteeNumber(product.getMinimumGuaranteeNumber())
                .fleet(product.getFleet())
                .hasReduction(product.isHasReduction())
                .visibility(product.getVisibility())
                .build();
    }
}

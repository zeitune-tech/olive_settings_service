package sn.zeitune.oliveinsurancesettings.app.specifications;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.util.UUID;

@Slf4j
public class ProductSpecification {

    public static Specification<Product> nameLike(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> branchLike(String branchName) {
        return (root, query, cb) -> branchName == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + branchName.toLowerCase() + "%");
    }

    public static Specification<Product> fleetIs(Boolean fleet) {
        return (root, query, cb) -> fleet == null ? null :
                cb.equal(root.get("fleet"), fleet);
    }

    public static Specification<Product> hasReductionIs(Boolean hasReduction) {
        return (root, query, cb) -> hasReduction == null ? null :
                cb.equal(root.get("hasReduction"), hasReduction);
    }

    public static Specification<Product> minRiskGreaterThanOrEqual(Integer minRisk) {
        return (root, query, cb) -> minRisk == null ? null :
                cb.greaterThanOrEqualTo(root.get("minRisk"), minRisk);
    }

    public static Specification<Product> maxRiskLessThanOrEqual(Integer maxRisk) {
        return (root, query, cb) -> maxRisk == null ? null :
                cb.lessThanOrEqualTo(root.get("maxRisk"), maxRisk);
    }

    public static Specification<Product> ownerEquals(UUID owner) {
        final String visibility ="PUBLIC";

        return (root, query, cb) -> {
            if (owner == null) {
                return null;
            }

            return cb.or(
                    cb.equal(root.get("owner"), owner),
                    cb.and(
                            cb.equal(root.get("visibility"), visibility),
                            cb.isMember(owner, root.get("sharedWithCompanies"))
                    )
            );
        };
    }


    public static Specification<Product> withFilters(
            String name,
            String branchName,
            Integer minRisk,
            Integer maxRisk,
            Boolean fleet,
            Boolean hasReduction,
            UUID owner
    ) {
        return Specification.where(nameLike(name))
                .and(ownerEquals(owner))
                .and(branchLike(branchName))
                .and(minRiskGreaterThanOrEqual(minRisk))
                .and(maxRiskLessThanOrEqual(maxRisk))
                .and(fleetIs(fleet))
                .and(hasReductionIs(hasReduction));
    }

}

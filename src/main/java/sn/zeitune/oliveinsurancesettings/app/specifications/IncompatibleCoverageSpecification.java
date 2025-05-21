package sn.zeitune.oliveinsurancesettings.app.specifications;

import org.springframework.data.jpa.domain.Specification;
import sn.zeitune.oliveinsurancesettings.app.entities.IncompatibleCoverage;

import java.util.UUID;

public class IncompatibleCoverageSpecification {

    public static Specification<IncompatibleCoverage> productEqual(UUID product) {
        return (root, query, cb) -> product == null ? null :
                cb.equal(root.get("coverage").get("product"), product);
    }

    public static Specification<IncompatibleCoverage> managementEntityEqual(UUID managementEntity) {
        return (root, query, cb) -> managementEntity == null ? null :
                cb.equal(root.get("managementEntity"), managementEntity);
    }

    public static Specification<IncompatibleCoverage> withFilters(
            UUID product,
            UUID managementEntity
    ) {
        return Specification.where(productEqual(product))
                .and(managementEntityEqual(managementEntity));
    }
}

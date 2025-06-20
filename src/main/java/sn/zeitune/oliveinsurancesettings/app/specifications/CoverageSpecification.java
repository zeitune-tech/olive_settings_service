package sn.zeitune.oliveinsurancesettings.app.specifications;


import org.springframework.data.jpa.domain.Specification;
import sn.zeitune.oliveinsurancesettings.app.entities.Coverage;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;

import java.util.UUID;

public class CoverageSpecification {

    public static Specification<Coverage> natureLike(String nature) {
        return (root, query, cb) -> nature == null ? null :
                cb.like(cb.lower(root.get("nature")), "%" + nature.toLowerCase() + "%");
    }

    public static Specification<Coverage> isFree(Boolean isFree) {
        return (root, query, cb) -> isFree == null ? null :
                cb.equal(root.get("isFree"), isFree);
    }

    public static Specification<Coverage> isFixed(Boolean isFixed) {
        return (root, query, cb) -> isFixed == null ? null :
                cb.equal(root.get("isFixed"), isFixed);
    }

    public static Specification<Coverage> calculationModeEquals(CalculationMode mode) {
        return (root, query, cb) -> mode == null ? null :
                cb.equal(root.get("calculationMode"), mode);
    }

    public static Specification<Coverage> productEquals(UUID productUuid) {
        return (root, query, cb) -> productUuid == null ? null :
                cb.equal(root.get("product"), productUuid);
    }

    public static Specification<Coverage> managementEntityEquals(UUID entityUuid) {

        final String visibility ="PUBLIC";

        return (root, query, cb) -> {
            if (entityUuid == null) {
                return null;
            }

            return cb.or(
                    cb.equal(root.get("managementEntity"), entityUuid),
                    cb.and(
                            cb.equal(root.get("product").get("visibility"), visibility),
                            cb.isMember(entityUuid, root.get("product").get("sharedWithCompanies"))
                    )
            );
        };
    }

    public static Specification<Coverage> withFilters(
            String nature,
            String designation,
            Boolean isFree,
            Boolean isFixed,
            CalculationMode calculationMode,
            UUID productUuid,
            UUID managementEntityUuid
    ) {
        return Specification.where(natureLike(nature))
                .and(isFree(isFree))
                .and(isFixed(isFixed))
                .and(calculationModeEquals(calculationMode))
                .and(productEquals(productUuid))
                .and(managementEntityEquals(managementEntityUuid));
    }
}

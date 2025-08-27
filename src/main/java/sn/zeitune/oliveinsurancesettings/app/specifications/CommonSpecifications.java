package sn.zeitune.oliveinsurancesettings.app.specifications;

import org.springframework.data.jpa.domain.Specification;

public class CommonSpecifications {

    public static <T> Specification<T> containsInsensitive(String attribute, String q) {
        return (root, query, cb) -> q == null || q.isBlank() ? null :
                cb.like(cb.lower(root.get(attribute)), "%" + q.toLowerCase() + "%");
    }

    public static <T> Specification<T> or(Specification<T> left, Specification<T> right) {
        return (root, query, cb) -> cb.or(left.toPredicate(root, query, cb), right.toPredicate(root, query, cb));
    }

    public static <T> Specification<T> actifEquals(Boolean actif) {
        return (root, query, cb) -> actif == null ? null : cb.equal(root.get("actif"), actif);
    }

    public static <T> Specification<T> includeDeleted(Boolean includeDeleted) {
        return (root, query, cb) -> Boolean.TRUE.equals(includeDeleted) ? null : cb.isFalse(root.get("deleted"));
    }
}


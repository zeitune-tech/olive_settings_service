package sn.zeitune.oliveinsurancesettings.enums;

import lombok.Getter;

@Getter
public enum MotorizationType {

    DIESEL("Diesel"),
    ESSENCE("Essence"),
    GPL("GPL"),
    ELECTRIC("Électrique"),
    HYBRID("Hybride");

    private final String label;

    MotorizationType(String label) {
        this.label = label;
    }

    public static MotorizationType fromLabel(String name) {
        for (MotorizationType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant " + MotorizationType.class.getCanonicalName() + "." + name);
    }

}

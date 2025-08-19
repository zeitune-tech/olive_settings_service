package sn.zeitune.oliveinsurancesettings.enums;

import lombok.Getter;

@Getter
public enum BodyWorkType {

    SEDAN("Sedan"),
    COUPE("Coupé"),
    CONVERTIBLE("Cabriolet"),
    HATCHBACK("Hatchback"),
    STATION_WAGON("Break"),
    SUV("SUV"),
    PICKUP("Pick-up"),
    VAN("Fourgon"),
    MINIVAN("Monospace"),
    BUS("Bus"),
    MPV("MPV");

    private final String label;

    BodyWorkType(String label) {
        this.label = label;
    }

    public static BodyWorkType fromLabel(String label) {
        for (BodyWorkType type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No BodyWorkType found for label: " + label);
    }

}

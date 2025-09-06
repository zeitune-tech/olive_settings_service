package sn.zeitune.oliveinsurancesettings.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum FieldType {
    SELECT("SELECT", "TEXT", "REFERENCE"),
    NUMBER("NUMBER", "NUMERIC");

    private final List<String> types;
    FieldType(String... types) {
        this.types = Arrays.asList(types);
    }

    public static FieldType fromString(String type) {
        for (FieldType fieldType : FieldType.values()) {
            if (fieldType.getTypes().contains(type)) {
                return fieldType;
            }
        }
        throw new IllegalArgumentException("Unknown field type: " + type);
    }
}

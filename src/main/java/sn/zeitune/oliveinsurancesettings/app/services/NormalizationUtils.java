package sn.zeitune.oliveinsurancesettings.app.services;

public final class NormalizationUtils {
    private NormalizationUtils() {}

    public static String normalizeCode(String input) {
        if (input == null) return null;
        String trimmed = input.trim();
        // Replace spaces and hyphens with underscore, remove duplicate underscores, keep alphanumerics and underscores
        String replaced = trimmed.replaceAll("[\u00A0\s-]+", "_");
        replaced = replaced.replaceAll("[^A-Za-z0-9_]", "_");
        replaced = replaced.replaceAll("_+", "_");
        return replaced.toUpperCase();
    }

    public static String normalizeLibelle(String input) {
        if (input == null) return null;
        String trimmed = input.trim();
        return trimmed.replaceAll("[\u00A0\s]+", " ");
    }
}


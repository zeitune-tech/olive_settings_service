package sn.zeitune.oliveinsurancesettings.app.dtos.requests;

public record ProductUpdate(
    String name,
    String description,
    int minRisk,
    int maxRisk,
    int minimumGuaranteeNumber,
    boolean fleet,
    boolean hasReduction
) {
}

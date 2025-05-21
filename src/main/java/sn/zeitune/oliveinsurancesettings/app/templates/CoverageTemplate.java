package sn.zeitune.oliveinsurancesettings.app.templates;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CoverageReferenceRequest;

import java.util.List;

public class CoverageTemplate {

    public static List<CoverageReferenceRequest> coverageReferenceRequests() {

        return List.of(
                CoverageReferenceRequest.builder()
                        .designation("Responsabilité Civile")
                        .family("Base Obligatoire")
                        .accessCharacteristic(true)
                        .tariffAccess(true)
                        .build(),

                CoverageReferenceRequest.builder()
                        .designation("Vol")
                        .family("Garanties Complémentaires")
                        .accessCharacteristic(true)
                        .tariffAccess(true)
                        .build(),

                CoverageReferenceRequest.builder()
                        .designation("Incendie")
                        .family("Garanties Complémentaires")
                        .accessCharacteristic(true)
                        .tariffAccess(true)
                        .build(),

                CoverageReferenceRequest.builder()
                        .designation("Bris de glace")
                        .family("Garanties Complémentaires")
                        .accessCharacteristic(true)
                        .tariffAccess(true)
                        .build(),

                CoverageReferenceRequest.builder()
                        .designation("Catastrophes Naturelles")
                        .family("Garanties Complémentaires")
                        .accessCharacteristic(true)
                        .tariffAccess(true)
                        .build(),

                CoverageReferenceRequest.builder()
                        .designation("Tempête / Événements climatiques")
                        .family("Garanties Complémentaires")
                        .accessCharacteristic(true)
                        .tariffAccess(true)
                        .build(),

                CoverageReferenceRequest.builder()
                        .designation("Attentats / Actes de terrorisme")
                        .family("Garanties Complémentaires")
                        .accessCharacteristic(true)
                        .tariffAccess(false)
                        .build(),

                CoverageReferenceRequest.builder()
                        .designation("Dommages Tous Accidents")
                        .family("Tous Risques")
                        .accessCharacteristic(true)
                        .tariffAccess(true)
                        .build(),

                CoverageReferenceRequest.builder()
                        .designation("Assistance 0 km")
                        .family("Services & Assistance")
                        .accessCharacteristic(false)
                        .tariffAccess(true)
                        .build(),

                CoverageReferenceRequest.builder()
                        .designation("Véhicule de Remplacement")
                        .family("Services & Assistance")
                        .accessCharacteristic(false)
                        .tariffAccess(true)
                        .build(),

                CoverageReferenceRequest.builder()
                        .designation("Protection Juridique")
                        .family("Services & Assistance")
                        .accessCharacteristic(false)
                        .tariffAccess(true)
                        .build(),

                CoverageReferenceRequest.builder()
                        .designation("Individuelle Conducteur")
                        .family("Garantie du Conducteur")
                        .accessCharacteristic(true)
                        .tariffAccess(true)
                        .build()
        );
    }
}

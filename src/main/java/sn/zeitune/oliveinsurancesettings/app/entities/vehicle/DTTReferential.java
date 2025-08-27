package sn.zeitune.oliveinsurancesettings.app.entities.vehicle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "referentiel_vehicule_dtt")
@Table(name = "referentiel_vehicule_dtt", indexes = {@Index(name = "idx_registration_number", columnList = "registrationNumber")})

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DTTReferential extends BaseEntity {

    @Id
    @Column(name = "code_referentiel_vehicule_dtt")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @Column(unique = true)
    private String registrationNumber; // Numéro d'immatriculation du véhicule

    @ManyToOne
    @JoinColumn(name = "model_code_modele_marque")
    private Model model;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }
}
package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;

import java.util.UUID;

@Data
@Builder
@Entity(name = "garantie")
@NoArgsConstructor
@AllArgsConstructor
public class Coverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_garantie")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;


    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "nature")
    private String nature;
    @Column(name = "gratuite")
    @Builder.Default
    private boolean isFree = false;
    @Column(name = "forfaitaire")
    @Builder.Default
    private boolean isFixed = false;
    @Column(name = "mode_de_calcul")
    @Builder.Default
    private CalculationMode calculationMode = CalculationMode.FIXE;
    @Column(name = "capital_fixe")
    @Builder.Default
    private Long fixedCapital = 0L;
    @Column(name = "capital_min")
    @Builder.Default
    private Long minCapital = 0L;
    @Column(name = "capital_max")
    @Builder.Default
    private Long maxCapital = 0L;
    @Column(name = "ordre")
    private int order;
    @Column(name = "prorota")
    private String prorata;
    @Column(name = "afficher_prime")
    @Builder.Default
    private boolean displayPrime = false;
    @Column(name = "gene_caracteristique")
    @Builder.Default
    private boolean generatesCharacteristic = false;

    /// Internal references
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "code_ref_garantie")
    private CoverageReference coverageReference;

    /// External references
    @Column(name = "code_produit")
    private UUID product;

    @Column(name = "code_entite")
    private UUID managementEntity;
}

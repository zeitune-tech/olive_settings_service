package sn.zeitune.oliveinsurancesettings.app.entities.coverage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.enums.CalculationMode;
import sn.zeitune.oliveinsurancesettings.enums.CoverageNature;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "produits_garanties")
@NoArgsConstructor
@AllArgsConstructor
public class Coverage extends BaseEntity {

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
    private CoverageNature nature;
    @Column(name = "gratuite")
    @Builder.Default
    private boolean isFree = false;
    @Column(name = "forfaitaire")
    @Builder.Default
    private boolean isFlatRate = false;
    @Column(name = "mode_de_calcul")
    @Builder.Default
    private CalculationMode calculationMode = CalculationMode.AUTOMATIC;
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
    private boolean prorata;
    @Column(name = "afficher_prime")
    @Builder.Default
    private boolean displayPrime = false;
    @Column(name = "gene_caracteristique")
    @Builder.Default
    private boolean generatesCharacteristic = false;
    @Builder.Default
    @Column(name = "clause")
    private String clause = "";
    @Builder.Default
    @Column(name = "libelle")
    private String label = "";

    /// Internal references
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "code_ref_garantie")
    private CoverageReference coverageReference;

    @ManyToOne
    @JoinColumn(name = "code_produit", nullable = false)
    private Product product;

    @Column(name = "code_entite")
    private UUID managementEntity;
}

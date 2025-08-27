package sn.zeitune.oliveinsurancesettings.app.entities.vehicle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.enums.BodyWorkType;
import sn.zeitune.oliveinsurancesettings.enums.MotorizationType;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "referentiel_vehicule_modele")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Model extends BaseEntity {

    @Id
    @Column(name = "code_modele_marque")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String name;

    @Enumerated(EnumType.STRING)
    private MotorizationType motorizationType;

    @Enumerated(EnumType.STRING)
    private BodyWorkType bodywork;

    private Integer placeCount;

    private Boolean hasTurbo;

    private BigDecimal horsepower;

    private BigDecimal displacement; // cylindrée en litres

    private BigDecimal weight; // poids en kilogrammes

    private String nature; // Nature du véhicule (ex: particulier, utilitaire, etc.)

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }
}

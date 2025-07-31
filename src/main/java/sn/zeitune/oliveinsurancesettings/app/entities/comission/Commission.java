package sn.zeitune.oliveinsurancesettings.app.entities.comission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "commissions")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Commission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_commission")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "date_effective", nullable = false)
    private LocalDate dateEffective;

    @Column(name = "management_rate", nullable = false)
    private Double managementRate;

    @Column(name = "taux", nullable = false)
    private Double contributionRate;

    @ManyToOne
    @JoinColumn(name = "code_produit", nullable = false)
    private Product product;


    @Column(name = "entite_gestion", nullable = false)
    private UUID managementEntity;
}

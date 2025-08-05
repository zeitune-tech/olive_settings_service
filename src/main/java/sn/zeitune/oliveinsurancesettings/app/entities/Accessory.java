package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.enums.AccessoryActType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity(name = "accessoires")
@NoArgsConstructor
@AllArgsConstructor
public class Accessory  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "act_type", nullable = false)
    private AccessoryActType actType;

    @Column(name = "accessory_amount", nullable = false)
    private Double accessoryAmount;

    @Column(name = "accessoire_risk", nullable = false)
    private Double accessoryRisk;

    @Column(name = "jour", nullable = false)
    private Integer day;
    @Column(name = "heure", nullable = false)
    private Integer hour;
    @Column(name = "minute", nullable = false)
    private Integer minute;

    @ManyToOne
    @JoinColumn(name = "code_produit", nullable = false)
    private Product product;

    @Column(name="code_entite_gestion", nullable = false)
    private UUID managementEntity;
}

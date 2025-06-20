package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.enums.CommissionTaxType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "commissions_taxe")
@NoArgsConstructor
@AllArgsConstructor
public class CommissionTax extends BaseEntity {

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
    @Column(name = "commission_tax_type", nullable = false)
    private CommissionTaxType commissionTaxType;

    @Column(name = "rate")
    private Double rate;

    @ManyToOne
    @JoinColumn(name = "code_produit", nullable = false)
    private Product product;

    @Column(name = "code_point_de_vente", nullable = false)
    private UUID pointOfSale;

    @Column(name = "code_entite", nullable = false)
    private UUID managementEntity;

}

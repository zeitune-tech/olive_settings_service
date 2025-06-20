package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "bases_taxe")
@NoArgsConstructor
@AllArgsConstructor
public class BaseTax extends BaseEntity {

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
    @Column(name = "calculation_base", nullable = false)
    private CalculationBase calculationBase;

    @Column(name = "is_flat", nullable = false)
    private boolean isFlat;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "fixed_amount")
    private Double fixedAmount;

    // Foreign keys
    @ManyToOne
    @JoinColumn(name = "tax_id")
    private Tax tax;

    @ManyToOne
    @JoinColumn(name = "code_garantie", nullable = true)
    private CoverageReference coverage;


    @ManyToOne
    @JoinColumn(name = "code_produit", nullable = false)
    private Product product;

    private UUID managementEntity;
}
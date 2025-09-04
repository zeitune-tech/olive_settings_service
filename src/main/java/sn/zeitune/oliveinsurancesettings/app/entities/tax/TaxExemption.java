package sn.zeitune.oliveinsurancesettings.app.entities.tax;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tax_exemptions")
public class TaxExemption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) this.uuid = UUID.randomUUID();
    }

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Propriétaire de la relation Many-to-Many.
     * Surtout PAS orphanRemoval / PAS CascadeType.REMOVE sur une M2M de référentiels.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tax_exemptions_types",
            joinColumns = @JoinColumn(name = "tax_exemption_id"),
            inverseJoinColumns = @JoinColumn(name = "tax_type_id"),
            uniqueConstraints = {
                    @UniqueConstraint(name = "uk_exemption_type", columnNames = {"tax_exemption_id", "tax_type_id"})
            }
    )
    private Set<TaxType> taxes = new HashSet<>();


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_tax_exemption_product"))
    private Product product;

    @Column(name = "management_entity")
    private UUID managementEntity;
}

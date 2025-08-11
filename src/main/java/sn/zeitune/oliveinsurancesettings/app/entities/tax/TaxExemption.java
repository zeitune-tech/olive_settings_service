package sn.zeitune.oliveinsurancesettings.app.entities.tax;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "exonérations_taxes")
public class TaxExemption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_taxe")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "nom", nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(
            name = "code_exoneration",
            referencedColumnName = "code_taxe",
            foreignKey = @ForeignKey(name = "fk_exoneration_taxes")
    )
    private Set<Tax> taxes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_produit", nullable = false)
    private Product product;

    @Column(name = "code_entite")
    private UUID managementEntity;
}

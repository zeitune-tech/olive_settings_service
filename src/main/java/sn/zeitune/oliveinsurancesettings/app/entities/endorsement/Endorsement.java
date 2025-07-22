package sn.zeitune.oliveinsurancesettings.app.entities.endorsement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity(name = "avenants")
@NoArgsConstructor
@AllArgsConstructor
public class Endorsement extends BaseEntity {

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

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "nature", nullable = false)
    NatureEndorsement nature;

    @ManyToMany
    @JoinTable(
        name = "avenants_products",
        joinColumns = @JoinColumn(name = "avenant_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> product;


    @Column(name = "entite_gestion", nullable = false)
    private UUID managementEntity;
}

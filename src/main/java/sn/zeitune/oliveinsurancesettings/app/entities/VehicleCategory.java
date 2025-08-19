package sn.zeitune.oliveinsurancesettings.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "vehicule_genre")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCategory extends BaseEntity {

    @Id
    @Column(name = "code_vehicule_genre")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "designation", nullable = false, unique = true)
    private String name; // Nom de la catégorie de véhicule

    @Column(name = "avecRemorque", nullable = false)
    private Boolean withTrailer;

    @Column(name = "avecChassis", nullable = false)
    private Boolean withChassis;

    @ManyToMany
    @JoinTable(
            name = "vehicule_genre_usage",
            joinColumns = @JoinColumn(name = "vehicule_genre_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicule_usage_id")
    )
    private Set<VehicleUsage> usages; // Liste des usages de véhicule associés à cette catégorie

    @ManyToMany
    @JoinTable(
            name = "vehicule_genre_produit",
            joinColumns = @JoinColumn(name = "vehicule_genre_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products; // Liste des produits associés à cette catégorie de véhicule

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }
}

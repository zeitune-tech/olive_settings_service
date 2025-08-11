package sn.zeitune.oliveinsurancesettings.app.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "points_de_vente_produits")
@NoArgsConstructor
@AllArgsConstructor
public class PointOfSaleProduct extends BaseEntity {

    @Id
    @Column(name = "code_pdv_prod")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @OneToMany
    @Builder.Default
    private Set<Product> products = new HashSet<>();

    @Column(name = "code_pdvc", nullable = false)
    private UUID pointOfSale;

    @Column(nullable = false, name = "code_comp")
    private UUID company;
}

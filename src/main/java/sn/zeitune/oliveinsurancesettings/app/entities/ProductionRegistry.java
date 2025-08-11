package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "registres_de_production")
@NoArgsConstructor
@AllArgsConstructor
public class ProductionRegistry  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_registre_production")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;


    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "prefixe")
    private String prefix;
    @Column(name = "longueur")
    private int length;
    @Column(name = "conteur")
    private int counter;

    /// External references
    @Column(name = "code_entite")
    private UUID managementEntity;

    @ManyToOne
    @JoinColumn(name = "code_produit", nullable = false)
    private Product product;
}

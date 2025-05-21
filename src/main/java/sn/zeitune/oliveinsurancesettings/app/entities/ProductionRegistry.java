package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Entity(name = "registres_de_production")
@NoArgsConstructor
@AllArgsConstructor
public class ProductionRegistry {

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

    @Column(name = "code_produit")
    private UUID product;

}

package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "registres_des_assures")
@NoArgsConstructor
@AllArgsConstructor
public class InsuredRegistry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_registre_assure")
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
    @Column(name = "counter")
    private int counter;

    /// External references
    @Column(name = "code_entite")
    private UUID managementEntity;
}

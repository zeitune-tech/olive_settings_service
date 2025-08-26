package sn.zeitune.oliveinsurancesettings.app.entities.settings;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "professions")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Profession extends BaseEntity {

    @Id
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

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Builder.Default
    @Column(nullable = false)
    private boolean actif = true;
}


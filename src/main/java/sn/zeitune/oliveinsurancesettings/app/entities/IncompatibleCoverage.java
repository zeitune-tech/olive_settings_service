package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "garanties_incompatibles")
@NoArgsConstructor
@AllArgsConstructor
public class IncompatibleCoverage  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_garantie_incompatible")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @ManyToOne
    @JoinColumn(name = "code_garantie_1")
    private Coverage coverage;

    @ManyToOne
    @JoinColumn(name = "code_garantie_2")
    private Coverage incompatibleCoverage;

    /// External references
    @Column(name = "code_entite")
    private UUID managementEntity;
}

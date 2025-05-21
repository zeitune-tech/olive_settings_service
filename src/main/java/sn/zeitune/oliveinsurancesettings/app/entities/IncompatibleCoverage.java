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
@Entity(name = "garantie_incompatible")
@NoArgsConstructor
@AllArgsConstructor
public class IncompatibleCoverage {

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

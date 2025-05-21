package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.zeitune.oliveinsurancesettings.enums.CoverageDurationType;
import sn.zeitune.oliveinsurancesettings.enums.Unit;

import java.util.UUID;

@Data
@Builder
@Entity(name = "duree_de_couverture")
@NoArgsConstructor
@AllArgsConstructor
public class CoverageDuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_duree")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;


    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }


    @Column(name = "de")
    private Double from;
    @Column(name = "a")
    private Double to;
    private CoverageDurationType type;
    @Column(name = "mode_de_prorata")
    private String prorotaMode;
    @Column(name = "unite")
    private Unit unit;

    /// External references
    @Column(name = "code_entite")
    private UUID managementEntity;
}

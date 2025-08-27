package sn.zeitune.oliveinsurancesettings.app.entities.coverage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.enums.CoverageDurationType;
import sn.zeitune.oliveinsurancesettings.enums.Unit;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "durees_de_couverture")
@NoArgsConstructor
@AllArgsConstructor
public class CoverageDuration extends BaseEntity {

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
    @Column(name = "designation")
    private String designation;
    @Column(name = "unite")
    private Unit unit;

    /// External references
    @Column(name = "code_entite")
    private UUID managementEntity;
}

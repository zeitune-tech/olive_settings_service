package sn.zeitune.oliveinsurancesettings.app.entities.coverage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "references_garantie")
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")

public class CoverageReference extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_ref_garantie")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;


    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "designation")
    private String designation;
    @Column(name = "famille")
    private String family;
    @Column(name = "acces_caracteristique")
    private boolean accessCharacteristic;
    @Column(name = "acces_tarif")
    private boolean tariffAccess;

    /// External references
    @Column(name = "code_entite")
    private UUID managementEntity;
}

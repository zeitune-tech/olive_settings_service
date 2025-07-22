package sn.zeitune.oliveinsurancesettings.app.entities.tax;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.enums.TaxGroup;
import sn.zeitune.oliveinsurancesettings.enums.TaxNature;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "taxes")
@NoArgsConstructor
@AllArgsConstructor
public class TaxType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_type_taxe")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature", nullable = false)
    private TaxNature nature;

    private UUID managementEntity;
}

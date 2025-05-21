package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsurancesettings.enums.TaxGroup;
import sn.zeitune.oliveinsurancesettings.enums.TaxNature;

import java.util.UUID;

@Data
@Builder
@Entity(name = "tax")
@NoArgsConstructor
@AllArgsConstructor
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "designation", nullable = false)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "grouping", nullable = false)
    private TaxGroup rgr;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature", nullable = false)
    private TaxNature nature;

    private UUID managementEntity;
}

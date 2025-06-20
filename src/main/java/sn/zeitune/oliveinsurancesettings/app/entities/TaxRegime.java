package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.enums.RegimeNature;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Builder
@Entity(name = "regimes_taxe")
@NoArgsConstructor
@AllArgsConstructor
public class TaxRegime extends BaseEntity {

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
    @Column(name = "nature", nullable = false)
    private RegimeNature nature;

    @Column(name = "stamp_exemption")
    private boolean stampExemption;

    @ManyToMany
    @JoinTable(
            name = "tax_regime_exempted_taxes",
            joinColumns = @JoinColumn(name = "regime_id"),
            inverseJoinColumns = @JoinColumn(name = "tax_id")
    )
    private Set<Tax> exemptedTaxes;

    private UUID managementEntity;
}

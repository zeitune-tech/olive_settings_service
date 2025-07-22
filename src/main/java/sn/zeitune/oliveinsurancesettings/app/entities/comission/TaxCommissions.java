package sn.zeitune.oliveinsurancesettings.app.entities.comission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "taxes_commissions")
public abstract class TaxCommissions extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_commission")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private TaxType taxType;

    @Column(name = "date_effective", nullable = false)
    private LocalDate dateEffective;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "a_retenir", nullable = false)
    private Boolean toWithhold;

    @Column(name = "entite_gestion", nullable = false)
    private UUID managementEntity;
}

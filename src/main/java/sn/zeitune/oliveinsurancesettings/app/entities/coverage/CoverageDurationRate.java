package sn.zeitune.oliveinsurancesettings.app.entities.coverage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "taux_duree")
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = false")
public class CoverageDurationRate extends BaseEntity {

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

    @Column(name = "date_effective", nullable = false)
    private LocalDate dateEffective;

    @ManyToOne(fetch = FetchType.EAGER)
    private CoverageDuration duration;

    @Column(name = "rate", nullable = false)
    private Double rate;


    @ManyToOne
    @JoinColumn(name = "code_produit", nullable = false)
    private Product product;

    private UUID managementEntity;
}

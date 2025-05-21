package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Entity(name = "commission_contributor")
@NoArgsConstructor
@AllArgsConstructor
public class CommissionContributor {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "commission_base", nullable = false)
    private CalculationBase commissionBase;

    @Column(name = "contributor_rate", nullable = false)
    private Double contributorRate;

    @Column(name = "upper_entity_contributor_rate")
    private Double upperEntityContributorRate;


    private UUID contributor;
    private UUID product;
    private UUID managementEntity;
}

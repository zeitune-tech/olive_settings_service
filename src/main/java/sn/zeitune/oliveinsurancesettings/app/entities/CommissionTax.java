package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.zeitune.oliveinsurancesettings.enums.CommissionTaxType;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Entity(name = "commission_tax")
@NoArgsConstructor
@AllArgsConstructor
public class CommissionTax {

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
    @Column(name = "commission_tax_type", nullable = false)
    private CommissionTaxType commissionTaxType;

    @Column(name = "rate")
    private Double rate;

    private UUID pointOfSale;
    private UUID product;
    private UUID managementEntity;

}

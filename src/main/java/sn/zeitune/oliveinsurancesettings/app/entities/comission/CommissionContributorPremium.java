package sn.zeitune.oliveinsurancesettings.app.entities.comission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.enums.CalculationBase;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "code_commission")
@Table(name = "commissions_apporteurs_primes")
public class CommissionContributorPremium extends CommissionContributor {

    @ManyToOne
    @JoinColumn(name = "code_garantie", nullable = false)
    private Coverage coverage;
}

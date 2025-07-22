package sn.zeitune.oliveinsurancesettings.app.entities.comission;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "code_commission")
@Table(name = "commissions_apporteurs_accessoires")
public class CommissionContributorAccessory extends CommissionContributor {
}

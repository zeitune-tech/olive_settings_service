package sn.zeitune.oliveinsurancesettings.app.entities.comission;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.Coverage;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "code_commission")
@Table(name = "commissions_points_de_vente_accessoires")
public class CommissionPointOfSaleAccessory extends CommissionPointOfSale{

}

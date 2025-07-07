package sn.zeitune.oliveinsurancesettings.app.entities.comission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.enums.PointOfSaleType;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "code_commission")
@Table(name = "commissions_points_de_vente")
public abstract class CommissionPointOfSale extends Commission {

    @Column(name = "point_de_vente", nullable = true)
    private String pointOfSaleCode;

    @Column(name = "type_point_de_vente", nullable = true)
    private PointOfSaleType pointOfSaleType;
}

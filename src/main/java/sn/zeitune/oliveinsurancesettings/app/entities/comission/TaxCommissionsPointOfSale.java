package sn.zeitune.oliveinsurancesettings.app.entities.comission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.BaseEntity;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.enums.CommissionTaxType;
import sn.zeitune.oliveinsurancesettings.enums.PointOfSaleType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity(name = "commissions_taxe")
@NoArgsConstructor
@AllArgsConstructor
public class TaxCommissionsPointOfSale extends TaxCommissions {

    @Column(name = "code_point_de_vente")
    private UUID pointOfSale;

    @Column(name = "type_point_de_vente")
    private PointOfSaleType pointOfSaleType;
}

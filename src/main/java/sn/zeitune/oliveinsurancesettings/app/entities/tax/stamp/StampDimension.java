package sn.zeitune.oliveinsurancesettings.app.entities.tax.stamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "timbres_dimension")
public class StampDimension extends Stamp {
    private String dimensionType;
}

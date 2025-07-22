package sn.zeitune.oliveinsurancesettings.app.entities.tax.stamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.Tax;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "timbres")
public abstract class Stamp extends Tax {

    @Column(name = "designation", nullable = false)
    private String designation;
}

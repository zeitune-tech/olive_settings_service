package sn.zeitune.oliveinsurancesettings.app.entities.comission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "taxes_commissions_apporteur")
public class TaxCommissionsContributor extends TaxCommissions {

    @Column(name = "apporteur")
    private UUID contributor;

    @Column(name = "type_apporteur")
    private UUID contributorType;
}

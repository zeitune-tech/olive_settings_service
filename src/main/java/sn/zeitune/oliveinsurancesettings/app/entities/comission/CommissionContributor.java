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

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "code_commission")
@Table(name = "commissions_apporteurs")
public abstract class CommissionContributor extends Commission {

    @Column(name = "apporteur", nullable = true)
    private UUID contributor;

    @Column(name = "apporteur_type", nullable = true)
    private UUID contributorType;
}

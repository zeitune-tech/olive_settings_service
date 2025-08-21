// sn/zeitune/oliveinsurancesettings/app/entities/endorsement/EndorsementNatureRank.java
package sn.zeitune.oliveinsurancesettings.app.entities.endorsement;

import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;

import java.util.UUID;

@Entity
@Table(
        name = "avenants_nature_ranks",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_rank_tenant_nature",
                columnNames = {"management_entity","nature"}
        ),
        indexes = @Index(name = "idx_rank_tenant", columnList = "management_entity")
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EndorsementNatureRank {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature", nullable = false, length = 64)
    private NatureEndorsement nature;

    @Column(name = "rank_value", nullable = false)
    private Integer rank;

    @Column(name = "management_entity", nullable = false)
    private UUID managementEntity;
}

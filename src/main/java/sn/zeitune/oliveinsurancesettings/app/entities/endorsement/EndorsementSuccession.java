package sn.zeitune.oliveinsurancesettings.app.entities.endorsement;

import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;

import java.util.UUID;

@Entity
@Table(
        name = "avenants_successions",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_succession_tenant_from_to",
                columnNames = {"management_entity","from_nature","to_nature"}
        ),
        indexes = {
                @Index(name = "idx_succession_tenant", columnList = "management_entity"),
                @Index(name = "idx_succession_from", columnList = "from_nature"),
                @Index(name = "idx_succession_to", columnList = "to_nature")
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EndorsementSuccession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_nature", nullable = false, length = 64)
    private NatureEndorsement fromNature;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_nature", nullable = false, length = 64)
    private NatureEndorsement toNature;

    @Column(name = "management_entity", nullable = false)
    private UUID managementEntity;
}

package sn.zeitune.oliveinsurancesettings.app.entities.endorsement;

import jakarta.persistence.*;
import lombok.*;
import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;

@Entity
@Table(
        name = "avenants_successions",
        uniqueConstraints = @UniqueConstraint(name = "uk_avenants_successions_from_to", columnNames = {"from_nature","to_nature"})
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EndorsementSuccession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_nature", nullable = false, length = 64)
    private NatureEndorsement fromNature;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_nature", nullable = false, length = 64)
    private NatureEndorsement toNature;
}

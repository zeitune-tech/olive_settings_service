package sn.zeitune.oliveinsurancesettings.app.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "branches")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Branch extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_seq")
    @SequenceGenerator(name = "branch_seq", sequenceName = "branch_seq", initialValue = 11, allocationSize = 1)
    @Column(name = "code_bran")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }


    @Column(name = "libelle")
    private String name;
    @Column(name = "description")
    private String description;

    @JoinColumn(name = "code_clas")
    @ManyToOne
    private Category category;
}

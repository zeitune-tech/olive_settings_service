package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Entity(name = "reference_garantie")
@NoArgsConstructor
@AllArgsConstructor
public class CoverageReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_ref_garantie")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;


    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "designation")
    private String designation;
    @Column(name = "famille")
    private String family;
    @Column(name = "acces_caracteristique")
    private boolean accessCharacteristic;
    @Column(name = "acces_tarif")
    private boolean tariffAccess;

    /// External references
    @Column(name = "code_entite")
    private UUID managementEntity;
}

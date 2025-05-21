package sn.zeitune.oliveinsurancesettings.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.zeitune.oliveinsurancesettings.enums.ClosureType;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Entity(name = "cloture")
@NoArgsConstructor
@AllArgsConstructor
public class Closure {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "code_duree")
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;


    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    @Column(name = "type")
    private ClosureType type;
    @Column(name = "date_effet")
    private LocalDate date;

    /// External references
    @Column(name = "code_entite")
    private UUID managementEntity;
}

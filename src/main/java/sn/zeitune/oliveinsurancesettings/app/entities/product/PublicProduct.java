package sn.zeitune.oliveinsurancesettings.app.entities.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("PUBLIC")
public class PublicProduct extends Product {

    @ElementCollection(fetch = FetchType.EAGER) // ou LAZY si tu veux
    @CollectionTable(
            name = "compagnies_public_produits",
            joinColumns = @JoinColumn(name = "code_produit")
    )
    @Column(name = "code_comp")
    private Set<UUID> sharedWithCompanies = new HashSet<>();
}

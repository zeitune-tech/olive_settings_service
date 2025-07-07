package sn.zeitune.oliveinsurancesettings.app.entities.tax;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sn.zeitune.oliveinsurancesettings.app.entities.coverage.CoverageReference;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "taxes_primes")
public class TaxPremiums extends Tax {

    @Column(name = "date_effective", nullable = false)
    private LocalDate dateEffective;

    @Column(name = "forfaitaire")
    private Boolean isFlatRate;

    @Column(name = "montant_forfaitaire")
    private Double flatRateAmount;

    @ManyToOne
    @JoinColumn(name = "code_type_taxe", nullable = false)
    private TaxType taxType;

    @ManyToOne
    @JoinColumn(name = "code_garantie")
    private CoverageReference coverage;

    @ManyToOne
    @JoinColumn(name = "code_produit", nullable = false)
    private Product product;
}

package sn.zeitune.oliveinsurancesettings.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.zeitune.oliveinsurancesettings.app.entities.PointOfSaleProduct;

import java.util.Optional;
import java.util.UUID;

public interface PointOfSaleProductRepository extends JpaRepository<PointOfSaleProduct, Long> {

    Optional<PointOfSaleProduct> findByUuid(UUID uuid);
    Page<PointOfSaleProduct> findByCompany(UUID company, Pageable pageable);
    Page<PointOfSaleProduct> findAllByPointOfSale(UUID pointOfSale, Pageable pageable);
}

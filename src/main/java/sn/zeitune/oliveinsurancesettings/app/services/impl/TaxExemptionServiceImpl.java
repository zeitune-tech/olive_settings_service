package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxExemptionRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxExemptionResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxTypeResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.product.Product;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxExemption;
import sn.zeitune.oliveinsurancesettings.app.entities.tax.TaxType;
import sn.zeitune.oliveinsurancesettings.app.mappers.BranchMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.ProductMapper;
import sn.zeitune.oliveinsurancesettings.app.mappers.TaxMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.ProductRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxExemptionRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.TaxTypeRepository;
import sn.zeitune.oliveinsurancesettings.app.services.TaxExemptionService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaxExemptionServiceImpl implements TaxExemptionService {

    private final TaxExemptionRepository taxExemptionRepository;
    private final ProductRepository productRepository;
    private final TaxTypeRepository taxTypeRepository;

    @Override
    public TaxExemptionResponse create(TaxExemptionRequest request, UUID managementEntity) {

        // 1) Produit
        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // 2) Types de taxe (depuis les UUIDs de la requête)
        List<UUID> ids = request.taxTypeIds() == null ? List.of() : request.taxTypeIds();
        List<TaxType> types = ids.isEmpty()
                ? List.of()
                : taxTypeRepository.findAllByUuidInAndDeletedFalse(ids);

        if (types.isEmpty()) {
            throw new IllegalArgumentException("No valid tax types found for the provided IDs.");
        }

        // 3) Construction + persist
        TaxExemption taxExemption = TaxMapper.map(request, product, new HashSet<>(types));
        taxExemption.setManagementEntity(managementEntity);
        taxExemptionRepository.save(taxExemption);

        // 4) Réponse
        var productResp = ProductMapper.map(product, BranchMapper.map(product.getBranch()), null);
        Set<TaxTypeResponse> typeResponses = types.stream()
                .map(TaxMapper::map) // TaxType -> TaxTypeResponse
                .collect(Collectors.toSet());

        return TaxMapper.map(taxExemption, productResp, typeResponses);
    }

    @Override
    public TaxExemptionResponse update(UUID uuid, TaxExemptionRequest request) {
        TaxExemption taxExemption = taxExemptionRepository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new IllegalArgumentException("TaxExemption not found"));

        Product product = productRepository.findByUuid(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        List<UUID> ids = request.taxTypeIds() == null ? List.of() : request.taxTypeIds();
        List<TaxType> types = ids.isEmpty()
                ? List.of()
                : taxTypeRepository.findAllByUuidInAndDeletedFalse(ids);

        if (types.isEmpty()) {
            throw new IllegalArgumentException("No valid tax types found for the provided IDs.");
        }

        taxExemption.setName(request.name());
        taxExemption.setProduct(product);
        taxExemption.setTaxes(new HashSet<>(types));
        taxExemptionRepository.save(taxExemption);

        var productResp = ProductMapper.map(product, BranchMapper.map(product.getBranch()), null);
        Set<TaxTypeResponse> typeResponses = types.stream()
                .map(TaxMapper::map)
                .collect(Collectors.toSet());

        return TaxMapper.map(taxExemption, productResp, typeResponses);
    }

    @Override
    public TaxExemptionResponse getByUuid(UUID uuid) {
        return taxExemptionRepository.findByUuidAndDeletedFalse(uuid)
                .map(te -> {
                    Product product = productRepository.findByUuid(te.getProduct().getUuid())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                    var productResp = ProductMapper.map(product, BranchMapper.map(product.getBranch()), null);
                    Set<TaxTypeResponse> typeResponses = te.getTaxes().stream()
                            .map(TaxMapper::map)
                            .collect(Collectors.toSet());

                    return TaxMapper.map(te, productResp, typeResponses);
                })
                .orElseThrow(() -> new IllegalArgumentException("TaxExemption not found"));
    }

    @Override
    public List<TaxExemptionResponse> getAllByManagementEntity(UUID managementEntity) {
        return taxExemptionRepository.findAllByManagementEntityAndDeletedFalse(managementEntity)
                .stream()
                .map(te -> {
                    Product product = productRepository.findByUuid(te.getProduct().getUuid())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                    var productResp = ProductMapper.map(product, BranchMapper.map(product.getBranch()), null);
                    Set<TaxTypeResponse> typeResponses = te.getTaxes().stream()
                            .map(TaxMapper::map)
                            .collect(Collectors.toSet());

                    return TaxMapper.map(te, productResp, typeResponses);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        TaxExemption te = taxExemptionRepository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(() -> new IllegalArgumentException("TaxExemption not found"));
        te.setDeleted(true);
        taxExemptionRepository.save(te);
    }
}

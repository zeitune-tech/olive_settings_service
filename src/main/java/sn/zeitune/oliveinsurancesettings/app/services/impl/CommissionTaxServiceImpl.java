package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.clients.AdministrationClient;
import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.CommissionTaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.CommissionTaxResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.CommissionTax;
import sn.zeitune.oliveinsurancesettings.app.mappers.CommissionTaxMapper;
import sn.zeitune.oliveinsurancesettings.app.repositories.CommissionTaxRepository;
import sn.zeitune.oliveinsurancesettings.app.services.CommissionTaxService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommissionTaxServiceImpl implements CommissionTaxService {

    private final CommissionTaxRepository repository;
    private final AdministrationClient administrationClient;

    @Override
    public CommissionTaxResponse create(CommissionTaxRequest request, UUID managementEntity) {
        CommissionTax entity = CommissionTaxMapper.map(request);
        entity.setManagementEntity(managementEntity);
        entity = repository.save(entity);

        return CommissionTaxMapper.map(
                entity,
                ProductResponseDTO.builder().id(entity.getProduct()).build()
        );
    }

    @Override
    public CommissionTaxResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(tax -> CommissionTaxMapper.map(
                        tax,
                        ProductResponseDTO.builder().id(tax.getProduct()).build()
                ))
                .orElseThrow(() -> new IllegalArgumentException("CommissionTax not found"));
    }

    @Override
    public List<CommissionTaxResponse> getAll(UUID managementEntity) {
        List<CommissionTax> taxes = repository.findAllByManagementEntity(managementEntity);

        List<ProductResponseDTO> productList = administrationClient.getByManagementEntity(managementEntity);
        Map<UUID, ProductResponseDTO> productMap = productList.stream()
                .collect(Collectors.toMap(ProductResponseDTO::id, p -> p));

        return taxes.stream()
                .map(tax -> {
                    ProductResponseDTO product = productMap.get(tax.getProduct());
                    return CommissionTaxMapper.map(tax, product);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID uuid) {
        CommissionTax tax = repository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("CommissionTax not found"));
        repository.delete(tax);
    }
}

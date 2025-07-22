package sn.zeitune.oliveinsurancesettings.app.services;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxExemptionRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.TaxTypeRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxAccessoryResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxExemptionResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxPremiumResponse;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.TaxTypeResponse;

import java.util.List;
import java.util.UUID;

public interface TaxService {

    // --- TaxPremium ---
    TaxPremiumResponse createTaxPremium(TaxRequest request);
    TaxPremiumResponse updateTaxPremium(UUID uuid, TaxRequest request);
    TaxPremiumResponse getTaxPremiumByUuid(UUID uuid);
    List<TaxPremiumResponse> getAllTaxPremiumsByManagementEntity(UUID managementEntity);
    void deleteTaxPremiumByUuid(UUID uuid);

    // --- TaxAccessory ---
    TaxAccessoryResponse createTaxAccessory(TaxRequest request);
    TaxAccessoryResponse updateTaxAccessory(UUID uuid, TaxRequest request);
    TaxAccessoryResponse getTaxAccessoryByUuid(UUID uuid);
    List<TaxAccessoryResponse> getAllTaxAccessoriesByManagementEntity(UUID managementEntity);
    void deleteTaxAccessoryByUuid(UUID uuid);

    // --- TaxExemption ---
    TaxExemptionResponse createTaxExemption(TaxExemptionRequest request);
    TaxExemptionResponse updateTaxExemption(UUID uuid, TaxExemptionRequest request);
    TaxExemptionResponse getTaxExemptionByUuid(UUID uuid);
    List<TaxExemptionResponse> getAllTaxExemptionsByManagementEntity(UUID managementEntity);
    void deleteTaxExemptionByUuid(UUID uuid);

    // --- TaxType ---
    TaxTypeResponse createTaxType(TaxTypeRequest request);
    TaxTypeResponse updateTaxType(UUID uuid, TaxTypeRequest request);
    TaxTypeResponse getTaxTypeByUuid(UUID uuid);
    List<TaxTypeResponse> getAllTaxTypes();
    void deleteTaxTypeByUuid(UUID uuid);
}

package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;
import sn.zeitune.oliveinsurancesettings.enums.CommissionTaxType;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CommissionTaxResponse(
        UUID id,
        LocalDate dateEffective,
        CommissionTaxType commissionTaxType,
        Double rate,
        UUID pointOfSale,
        ProductResponse product,
        UUID managementEntityId
) {}

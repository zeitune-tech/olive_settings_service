package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

import sn.zeitune.oliveinsurancesettings.app.dtos.externals.ProductResponseDTO;
import sn.zeitune.oliveinsurancesettings.enums.AccessoryActType;

@Builder
public record AccessoryResponse(
        UUID id,
        LocalDate dateEffective,
        AccessoryActType actType,
        Double accessoryAmount,
        ProductResponseDTO product,
        UUID managementEntityId
) {}

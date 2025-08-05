package sn.zeitune.oliveinsurancesettings.app.dtos.responses;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

import sn.zeitune.oliveinsurancesettings.enums.AccessoryActType;

@Builder
public record AccessoryResponse(
        UUID id,
        LocalDate dateEffective,
        AccessoryActType actType,
        Double accessoryAmount,
        Double accessoryRisk,
        Integer day,
        Integer hour,
        Integer minute,
        ProductResponse product,
        UUID managementEntityId
) {}

package sn.zeitune.oliveinsurancesettings.app.mappers;

import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleModelRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleModelResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Model;
import sn.zeitune.oliveinsurancesettings.enums.BodyWorkType;
import sn.zeitune.oliveinsurancesettings.enums.MotorizationType;

public class VehicleModelMapper {

    public static void putOnEntity (VehicleModelRequestDTO vehicleModelRequest, Model model) {

    }

    public static Model map(VehicleModelRequestDTO vehicleModelRequest) {
        if (vehicleModelRequest == null) return null;
        return Model.builder()
                .name(vehicleModelRequest.name().toUpperCase())
                .motorizationType(
                        vehicleModelRequest.motorizationType() != null
                                ? MotorizationType.fromLabel(vehicleModelRequest.motorizationType())
                                : null
                )
                .bodywork(
                        vehicleModelRequest.bodywork() != null
                                ? BodyWorkType.fromLabel(vehicleModelRequest.bodywork())
                                : null
                )
                .placeCount(vehicleModelRequest.placeCount())
                .hasTurbo(vehicleModelRequest.hasTurbo())
                .horsepower(vehicleModelRequest.horsepower())
                .displacement(vehicleModelRequest.displacement())
                .weight(vehicleModelRequest.weight())
                .nature(vehicleModelRequest.nature())
                .build();
    }

    public static VehicleModelResponseDTO map(Model vehicleModel) {
        if (vehicleModel == null) return null;
        return VehicleModelResponseDTO.builder()
                .id(vehicleModel.getUuid())
                .name(vehicleModel.getName())
                .motorizationType(
                        vehicleModel.getMotorizationType() != null
                                ? vehicleModel.getMotorizationType().name()
                                : null
                )
                .bodywork(
                        vehicleModel.getBodywork() != null
                                ? vehicleModel.getBodywork().name()
                                : null
                )
                .placeCount(vehicleModel.getPlaceCount())
                .hasTurbo(vehicleModel.getHasTurbo())
                .horsepower(vehicleModel.getHorsepower())
                .displacement(vehicleModel.getDisplacement())
                .weight(vehicleModel.getWeight())
                .nature(vehicleModel.getNature())
                .build();
    }
}


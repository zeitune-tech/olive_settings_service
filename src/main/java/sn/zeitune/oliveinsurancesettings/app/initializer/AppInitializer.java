package sn.zeitune.oliveinsurancesettings.app.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleCategoryRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleDTTReferentialSimpleRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleModelRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.VehicleUsageRequestDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleBrandResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleCategoryResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleModelResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.VehicleUsageResponseDTO;
import sn.zeitune.oliveinsurancesettings.app.entities.vehicle.Brand;
import sn.zeitune.oliveinsurancesettings.app.services.*;
import sn.zeitune.oliveinsurancesettings.enums.BodyWorkType;
import sn.zeitune.oliveinsurancesettings.enums.MotorizationType;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppInitializer implements CommandLineRunner {

    private final BranchService branchService;

    private final VehicleReferentialService vehicleReferentialService;
    private final VehicleDTTReferentialService vehicleDTTReferentialService;

    private final VehicleUsageService vehicleUsageService;
    private final VehicleCategoryService vehicleCategoryService;

    @Override
    public void run(String... args) throws Exception {

        branchService.init();
        initVehicleReferential();
        initVehicleDTTReferential();
        initVehicleUsage();
        initVehicleCategories();
    }

    private void initVehicleReferential() {
        if (vehicleReferentialService.getAll(Pageable.ofSize(1)).stream().findAny().isPresent()) return;
        List<String> defaultBrandNames = List.of(
                "Toyota",
                "Hyundai",
                "Mercedes-Benz",
                "Isuzu",
                "Tata"
        );
        for (String brandName : defaultBrandNames) {
            VehicleBrandResponseDTO brandResponse = vehicleReferentialService.createBrand(brandName);

            switch (brandName) {
                case "Toyota" -> {
                    vehicleReferentialService.addNewModelToBrand(
                            brandResponse.id(),
                            VehicleModelRequestDTO.builder()
                                    .name("Hiace") // utilisé comme taxi-bus
                                    .motorizationType(MotorizationType.DIESEL.getLabel())
                                    .bodywork(BodyWorkType.VAN.getLabel())
                                    .placeCount(15)
                                    .hasTurbo(true)
                                    .horsepower(new BigDecimal("136"))
                                    .displacement(new BigDecimal("2.5"))
                                    .weight(new BigDecimal("1900"))
                                    .nature("Transport public")
                                    .build()
                    );
                    vehicleReferentialService.addNewModelToBrand(
                            brandResponse.id(),
                            VehicleModelRequestDTO.builder()
                                    .name("Coaster") // minibus 30 places
                                    .motorizationType(MotorizationType.DIESEL.getLabel())
                                    .bodywork(BodyWorkType.BUS.getLabel())
                                    .placeCount(30)
                                    .hasTurbo(true)
                                    .horsepower(new BigDecimal("150"))
                                    .displacement(new BigDecimal("4.0"))
                                    .weight(new BigDecimal("3500"))
                                    .nature("Transport public")
                                    .build()
                    );
                }
                case "Hyundai" -> {
                    vehicleReferentialService.addNewModelToBrand(
                            brandResponse.id(),
                            VehicleModelRequestDTO.builder()
                                    .name("H1 Minibus") // version taxi collectif
                                    .motorizationType(MotorizationType.DIESEL.getLabel())
                                    .bodywork(BodyWorkType.VAN.getLabel())
                                    .placeCount(12)
                                    .hasTurbo(true)
                                    .horsepower(new BigDecimal("170"))
                                    .displacement(new BigDecimal("2.5"))
                                    .weight(new BigDecimal("2100"))
                                    .nature("Transport public")
                                    .build()
                    );
                    vehicleReferentialService.addNewModelToBrand(
                            brandResponse.id(),
                            VehicleModelRequestDTO.builder()
                                    .name("County") // bus urbain
                                    .motorizationType(MotorizationType.DIESEL.getLabel())
                                    .bodywork(BodyWorkType.BUS.getLabel())
                                    .placeCount(30)
                                    .hasTurbo(true)
                                    .horsepower(new BigDecimal("140"))
                                    .displacement(new BigDecimal("3.9"))
                                    .weight(new BigDecimal("4000"))
                                    .nature("Transport public")
                                    .build()
                    );
                }
                case "Mercedes-Benz" -> {
                    vehicleReferentialService.addNewModelToBrand(
                            brandResponse.id(),
                            VehicleModelRequestDTO.builder()
                                    .name("Sprinter") // très utilisé comme minibus
                                    .motorizationType(MotorizationType.DIESEL.getLabel())
                                    .bodywork(BodyWorkType.VAN.getLabel())
                                    .placeCount(18)
                                    .hasTurbo(true)
                                    .horsepower(new BigDecimal("163"))
                                    .displacement(new BigDecimal("2.2"))
                                    .weight(new BigDecimal("2200"))
                                    .nature("Transport public")
                                    .build()
                    );
                    vehicleReferentialService.addNewModelToBrand(
                            brandResponse.id(),
                            VehicleModelRequestDTO.builder()
                                    .name("Citaro") // bus urbain
                                    .motorizationType(MotorizationType.DIESEL.getLabel())
                                    .bodywork(BodyWorkType.BUS.getLabel())
                                    .placeCount(100)
                                    .hasTurbo(true)
                                    .horsepower(new BigDecimal("299"))
                                    .displacement(new BigDecimal("7.7"))
                                    .weight(new BigDecimal("11500"))
                                    .nature("Transport public")
                                    .build()
                    );
                }
                case "Isuzu" -> {
                    vehicleReferentialService.addNewModelToBrand(
                            brandResponse.id(),
                            VehicleModelRequestDTO.builder()
                                    .name("NQR Bus") // car rapide typique
                                    .motorizationType(MotorizationType.DIESEL.getLabel())
                                    .bodywork(BodyWorkType.BUS.getLabel())
                                    .placeCount(30)
                                    .hasTurbo(true)
                                    .horsepower(new BigDecimal("155"))
                                    .displacement(new BigDecimal("5.2"))
                                    .weight(new BigDecimal("4500"))
                                    .nature("Transport public")
                                    .build()
                    );
                }
                case "Tata" -> {
                    vehicleReferentialService.addNewModelToBrand(
                            brandResponse.id(),
                            VehicleModelRequestDTO.builder()
                                    .name("Starbus") // bus scolaire/urbain
                                    .motorizationType(MotorizationType.DIESEL.getLabel())
                                    .bodywork(BodyWorkType.BUS.getLabel())
                                    .placeCount(55)
                                    .hasTurbo(true)
                                    .horsepower(new BigDecimal("180"))
                                    .displacement(new BigDecimal("5.9"))
                                    .weight(new BigDecimal("8000"))
                                    .nature("Transport public")
                                    .build()
                    );
                }
            }
        }
        System.out.println("Vehicle referential initialized with public transport brands and models.");
    }

    private void initVehicleDTTReferential() {
        if (vehicleDTTReferentialService.getAll(Pageable.ofSize(1)).stream().findAny().isPresent()) return;
        List<String> defaultDTTNames = List.of(
                "Toyota",
                "Tata"
        );
        for (String dttName : defaultDTTNames) {
            Brand brand = vehicleReferentialService.findBrandEntityByName(dttName);
            String modelName = vehicleReferentialService.getModelsByBrand(Pageable.ofSize(2), brand.getUuid()).stream().map(VehicleModelResponseDTO::name).findAny().get();

            System.out.println(dttName + " " + brand.getName() + " " + modelName);

            vehicleDTTReferentialService.create(
                    VehicleDTTReferentialSimpleRequestDTO.builder()
                            .brandName(dttName)
                            .modelName(modelName)
                            .registrationNumber("DK-" + dttName.toUpperCase() + "-" + modelName.toUpperCase())
                            .build()
            );
        }

    }

    private void initVehicleUsage () {
        if (vehicleUsageService.getVehicleUsagesListResponseDTO(Pageable.ofSize(1)).stream().findAny().isPresent()) return;
        VehicleUsageResponseDTO vehicleUsageResponseDTO = vehicleUsageService.createVehicleUsage(
                VehicleUsageRequestDTO.builder()
                        .name("Transport public")
                        .build()
        );
    }

    private void initVehicleCategories() {
        if (vehicleCategoryService.getVehicleCategoriesListResponseDTO(Pageable.ofSize(1)).stream().findAny().isPresent()) return;

        VehicleCategoryResponseDTO category1 = vehicleCategoryService.createVehicleCategory(
                VehicleCategoryRequestDTO.builder()
                        .name("Taxi-bus")
                        .withTrailer(false)
                        .withChassis(false)
                        .build()
        );

        VehicleCategoryResponseDTO category2 = vehicleCategoryService.createVehicleCategory(
                VehicleCategoryRequestDTO.builder()
                        .name("bus")
                        .withTrailer(false)
                        .withChassis(false)
                        .build()
        );

        System.out.println("Vehicle categories initialized with Taxi-bus category.");
    }
}

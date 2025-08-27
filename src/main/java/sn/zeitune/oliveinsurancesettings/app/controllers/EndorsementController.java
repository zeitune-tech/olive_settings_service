package sn.zeitune.oliveinsurancesettings.app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.EndorsementRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.EndorsementResponse;
import sn.zeitune.oliveinsurancesettings.app.services.EndorsementService;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/endorsements")
@RequiredArgsConstructor
public class EndorsementController {

    private final EndorsementService endorsementService;

    @GetMapping
    public List<EndorsementResponse> getEndorsements(
            Authentication authentication
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return endorsementService.findAllByManagementEntity(employee.getManagementEntity());
    }

    @GetMapping("/{uuid}")
    public EndorsementResponse getEndorsementByUuid(
            Authentication authentication,
            @PathVariable UUID uuid
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return endorsementService.findByUuid(uuid);
    }


    @PostMapping
    public EndorsementResponse createEndorsement(
            Authentication authentication,
            @RequestBody @Valid EndorsementRequest request
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return endorsementService.create(request, employee.getManagementEntity());
    }

    @PutMapping("/{endorsementUuid}/add-product/{productUuid}")
    public EndorsementResponse addProductToEndorsement(
            Authentication authentication,
            @PathVariable UUID endorsementUuid,
            @PathVariable UUID productUuid
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return endorsementService.addProductToEndorsement(endorsementUuid, productUuid);
    }

    @PutMapping("/{endorsementUuid}/remove-product/{productUuid}")
    public EndorsementResponse removeProductFromEndorsement(
            Authentication authentication,
            @PathVariable UUID endorsementUuid,
            @PathVariable UUID productUuid
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        return endorsementService.removeProductFromEndorsement(endorsementUuid, productUuid);
    }

    @DeleteMapping("/{uuid}")
    public void deleteEndorsement(
            Authentication authentication,
            @PathVariable UUID uuid
    ) {
        Employee employee = (Employee) authentication.getPrincipal();
        endorsementService.deleteByUuid(uuid);
        // Optionally, you can return a response indicating success
    }
}

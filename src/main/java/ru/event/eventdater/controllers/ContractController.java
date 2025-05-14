package ru.event.eventdater.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.event.eventdater.domain.Contract;
import ru.event.eventdater.requests.admin.CreateContractRequest;
import ru.event.eventdater.requests.principal.SignContractRequest;
import ru.event.eventdater.services.ContractService;

@RequestMapping("/api/contract")
@RestController
public class ContractController {
    public final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Ошибка авторизации")
    })
    @Operation(summary = "Создать договор на подпись")
    @PostMapping("/create")
    public ResponseEntity<Contract> createContract(@RequestBody @Valid CreateContractRequest request) {
        return ResponseEntity.ok(contractService.createContract(request));
    }

    @PreAuthorize("hasAuthority('PRINCIPAL')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Ошибка авторизации")
    })
    @Operation(summary = "Подписать договор")
    @PostMapping("/sign")
    public ResponseEntity<Contract> createContract(@RequestBody @Valid SignContractRequest request) {
        return ResponseEntity.ok(contractService.signContract(request));
    }
}

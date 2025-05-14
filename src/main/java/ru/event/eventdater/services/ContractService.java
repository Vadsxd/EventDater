package ru.event.eventdater.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.event.eventdater.domain.Contract;
import ru.event.eventdater.domain.Event;
import ru.event.eventdater.domain.enums.ContractStatus;
import ru.event.eventdater.repos.ContractRepo;
import ru.event.eventdater.repos.EventRepo;
import ru.event.eventdater.requests.admin.CreateContractRequest;
import ru.event.eventdater.requests.principal.SignContractRequest;

@Service
public class ContractService {
    private final ContractRepo contractRepo;
    private final EventRepo eventRepo;

    @Autowired
    public ContractService(ContractRepo contractRepo, EventRepo eventRepo) {
        this.contractRepo = contractRepo;
        this.eventRepo = eventRepo;
    }

    public void save(Contract contract) {
        contractRepo.save(contract);
    }

    @Transactional
    public Contract createContract(CreateContractRequest request) {
        Event event = eventRepo.findById(request.getEventId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данного мероприятия не зарегистрировано"));

        var contract = Contract.builder()
                .event(event)
                .contractStatus(ContractStatus.NEOK)
                .build();

        save(contract);

        return contract;
    }

    @Transactional
    public Contract signContract(SignContractRequest request) {
        Contract contract = contractRepo.findById(request.getContractId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данный контракт отсутствует"));

        contract.setContractStatus(ContractStatus.OK);
        save(contract);

        return contract;
    }
}

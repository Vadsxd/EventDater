package ru.event.eventdater.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.event.eventdater.domain.Contract;
import ru.event.eventdater.domain.enums.ContractStatus;
import ru.event.eventdater.repos.ContractRepo;
import ru.event.eventdater.requests.principal.SignContractRequest;

@Service
public class ContractService {
    private final ContractRepo contractRepo;

    @Autowired
    public ContractService(ContractRepo contractRepo) {
        this.contractRepo = contractRepo;
    }

    public void save(Contract contract) {
        contractRepo.save(contract);
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

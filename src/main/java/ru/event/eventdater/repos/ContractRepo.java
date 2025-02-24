package ru.event.eventdater.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.event.eventdater.domain.Contract;

@Repository
public interface ContractRepo extends JpaRepository<Contract, Long> {
}

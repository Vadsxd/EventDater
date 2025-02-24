package ru.event.eventdater.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.event.eventdater.domain.Event;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
}

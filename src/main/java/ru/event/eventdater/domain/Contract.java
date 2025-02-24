package ru.event.eventdater.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.event.eventdater.domain.enums.ContractStatus;

@Builder
@Entity
@Table(name = "contract")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    @Id
    @Column(name = "event_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ContractStatus contractStatus;

    @MapsId
    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;
}

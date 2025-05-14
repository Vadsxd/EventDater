package ru.event.eventdater.requests.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание заявки на подписание долговора")
public class CreateContractRequest {
    @Schema(description = "Идентификатор мероприятия, к которому привязан договор")
    @NotBlank(message = "Идентификатор не может отсутствовать")
    private Long eventId;
}

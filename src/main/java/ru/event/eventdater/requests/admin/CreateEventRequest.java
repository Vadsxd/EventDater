package ru.event.eventdater.requests.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Создание мероприятия")
public class CreateEventRequest {
    @Schema(description = "Название мероприятия")
    @NotBlank(message = "Название не может отсутствовать")
    private String name;

    @Schema(description = "Стоимость участия")
    @NotBlank(message = "Стоимость должна быть указана")
    private Long price;
}

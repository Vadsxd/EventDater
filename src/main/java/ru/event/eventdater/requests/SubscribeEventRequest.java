package ru.event.eventdater.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Записаться на мероприятие")
public class SubscribeEventRequest {
    @Schema(description = "Идентификатор мероприятия")
    @NotBlank(message = "Идентификатор не может отсутствовать")
    private Long id;

    @Schema(description = "ФИО")
    @NotBlank(message = "Данный пункт не может отсутствовать")
    private String FCs;

    @Schema(description = "Возраст")
    @NotBlank(message = "Данный пункт не может отсутствовать")
    private Long age;

    @Schema(description = "Пцр тест в виде ссылки")
    @NotBlank(message = "Данный пункт не может отсутствовать")
    private String pcrLink;
}

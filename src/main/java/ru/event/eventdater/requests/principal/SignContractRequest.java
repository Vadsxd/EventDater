package ru.event.eventdater.requests.principal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Подписание договора")
public class SignContractRequest {
    @Schema(description = "Идентификатор договора")
    @NotBlank(message = "Идентификатор не может отсутствовать")
    private Long contractId;
}

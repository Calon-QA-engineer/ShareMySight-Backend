package QAEngineer.ShareMySight.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AIPromptRequest {
    @NotBlank(message = "Id must not be blank")
    @NotNull(message = "Id must not be null")
    @NotEmpty(message = "Id must not be empty")
    private String promptId;
}

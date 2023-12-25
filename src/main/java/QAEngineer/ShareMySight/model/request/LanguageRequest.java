package QAEngineer.ShareMySight.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
public class LanguageRequest {
  @Field(name = "language_name")
  @NotEmpty(message = "Language name is required")
  @NotNull(message = "Language name is required")
  @NotBlank(message = "Language name is required")
  private String languageName;
}

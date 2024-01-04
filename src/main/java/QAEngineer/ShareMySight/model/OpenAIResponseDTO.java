package QAEngineer.ShareMySight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenAIResponseDTO {
    private String description;

    @SuppressWarnings("unchecked")
    @JsonProperty("choices")
    private void unpackNested(List<Object> choices) {
        if (choices != null && !choices.isEmpty()) {
            Map<String, Object> choice = (Map<String, Object>) choices.get(0);
            Map<String, String> message = (Map<String, String>) choice.get("message");
            this.description = message.get("content");
        }
    }
}

package QAEngineer.ShareMySight.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIPromptResponse {
    private String promptId;
    private String imageUrl;
    private String text;
    private String description;
}

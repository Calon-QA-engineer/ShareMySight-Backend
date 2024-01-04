package QAEngineer.ShareMySight.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIUploadResponse {
    private String id;
    private String imageUrl;
    private String text;
}

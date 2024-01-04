package QAEngineer.ShareMySight.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OpenAIRequestDTO {
    @JsonProperty("model")
    private String model;

    @JsonProperty("messages")
    private List<OpenAIMessageDTO> messages;

    @JsonProperty("max_tokens")
    private Integer maxTokens;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpenAITextContentDTO {
        @JsonProperty("type")
        private String type;

        @JsonProperty("text")
        private String text;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpenAIImageContentDTO {
        @JsonProperty("type")
        private String type;

        @JsonProperty("image_url")
        private OpenAIImageUrl imageUrl;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpenAIMessageDTO {
        private String role;
        private List<Object> content;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpenAIImageUrl {
        private String url;
    }
}

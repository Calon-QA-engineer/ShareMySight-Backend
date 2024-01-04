package QAEngineer.ShareMySight.service.serviceInteface;

import QAEngineer.ShareMySight.entity.AIChat;
import QAEngineer.ShareMySight.model.request.AIPromptRequest;
import QAEngineer.ShareMySight.model.response.AIPromptResponse;
import QAEngineer.ShareMySight.model.response.AIUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AIChatService {
    AIUploadResponse upload(MultipartFile imageUrl, String text);
    List<AIChat> getChatHistory();
    AIPromptResponse getAIResponse(AIPromptRequest request);
}

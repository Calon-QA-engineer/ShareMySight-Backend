package QAEngineer.ShareMySight.service.serviceInteface;

import QAEngineer.ShareMySight.entity.AIChat;
import QAEngineer.ShareMySight.model.response.AIChatResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AIChatService {
    AIChatResponse upload(MultipartFile imageUrl);
    List<AIChat> getChatHistory();
}

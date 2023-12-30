package QAEngineer.ShareMySight.service.serviceInteface;

import QAEngineer.ShareMySight.model.request.MessageRequest;

public interface ChatService {
    void addMessage(MessageRequest messageRequest);
}
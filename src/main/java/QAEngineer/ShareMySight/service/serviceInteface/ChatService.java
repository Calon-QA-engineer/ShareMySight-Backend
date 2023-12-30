package QAEngineer.ShareMySight.service.serviceInteface;

import QAEngineer.ShareMySight.entity.GlobalChat;
import QAEngineer.ShareMySight.model.request.MessageRequest;
import org.apache.logging.log4j.message.Message;

import java.security.Principal;
import java.util.List;

public interface ChatService {
    GlobalChat addMessage(MessageRequest messageRequest, Principal principal);
    List<GlobalChat> getMessages();
}
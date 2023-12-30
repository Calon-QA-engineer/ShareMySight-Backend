package QAEngineer.ShareMySight.service.serviceImpl;

import QAEngineer.ShareMySight.entity.User;
import QAEngineer.ShareMySight.model.request.MessageRequest;
import QAEngineer.ShareMySight.repository.ChatRepository;
import QAEngineer.ShareMySight.service.serviceInteface.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    public void addMessage(MessageRequest messageRequest) {
        log.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication()));
    }
}

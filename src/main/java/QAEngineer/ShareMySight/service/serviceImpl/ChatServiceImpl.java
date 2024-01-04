package QAEngineer.ShareMySight.service.serviceImpl;

import QAEngineer.ShareMySight.entity.GlobalChat;
import QAEngineer.ShareMySight.entity.User;
import QAEngineer.ShareMySight.exception.CustomException;
import QAEngineer.ShareMySight.model.UserDTO;
import QAEngineer.ShareMySight.model.request.MessageRequest;
import QAEngineer.ShareMySight.repository.ChatRepository;
import QAEngineer.ShareMySight.repository.UserRepository;
import QAEngineer.ShareMySight.service.serviceInteface.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public GlobalChat addMessage(MessageRequest messageRequest, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED));

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        GlobalChat newMessage = GlobalChat.builder()
                .message(messageRequest.getMessage())
                .user(userDTO)
                .statusRecord('A')
                .build();

        chatRepository.save(newMessage);
        return newMessage;
    }

    @Override
    public List<GlobalChat> getMessages() {
        return chatRepository.findAll();
    }
}

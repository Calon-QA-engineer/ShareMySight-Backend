package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.model.request.MessageRequest;
import QAEngineer.ShareMySight.service.serviceInteface.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageRequest sendMessage(MessageRequest messageRequest) {
        chatService.addMessage(messageRequest);
        return messageRequest;
    }

    @GetMapping("/test")
    public String test() {
        log.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication()));
        return "berhasil";
    }
}

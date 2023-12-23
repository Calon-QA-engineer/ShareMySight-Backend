package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.model.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDTO sendMessage(MessageDTO message) {
        return message;
    }
}

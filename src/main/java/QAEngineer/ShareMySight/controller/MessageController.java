package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.entity.GlobalChat;
import QAEngineer.ShareMySight.model.request.MessageRequest;
import QAEngineer.ShareMySight.model.response.StandardResponse;
import QAEngineer.ShareMySight.service.serviceInteface.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("app")
public class MessageController {
    private final ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public GlobalChat sendMessage(MessageRequest messageRequest, Principal principal) {
        return chatService.addMessage(messageRequest, principal);
    }

    @GetMapping("/chat")
    public ResponseEntity<StandardResponse<List<GlobalChat>>> getMessages() {
        List<GlobalChat> globalChats = chatService.getMessages();
        StandardResponse<List<GlobalChat>> response = StandardResponse.<List<GlobalChat>>builder()
                .message("success")
                .status(true)
                .data(globalChats)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

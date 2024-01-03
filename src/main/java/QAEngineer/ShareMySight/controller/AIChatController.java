package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.entity.AIChat;
import QAEngineer.ShareMySight.model.response.AIChatResponse;
import QAEngineer.ShareMySight.model.response.StandardResponse;
import QAEngineer.ShareMySight.service.serviceInteface.AIChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "ai-chat")
public class AIChatController {
    private final AIChatService aiChatService;

    @GetMapping
    public ResponseEntity<StandardResponse<List<AIChat>>> getChatHistory() {
        List<AIChat> messageHistory = aiChatService.getChatHistory();

        StandardResponse<List<AIChat>> response = StandardResponse.<List<AIChat>>builder()
                .status(true)
                .message("success")
                .data(messageHistory)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StandardResponse<AIChatResponse>> uploadImage(@RequestParam("file") MultipartFile file) {
        AIChatResponse message = aiChatService.upload(file);

        StandardResponse<AIChatResponse> response = StandardResponse.<AIChatResponse>builder()
                .status(true)
                .message("success")
                .data(message)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

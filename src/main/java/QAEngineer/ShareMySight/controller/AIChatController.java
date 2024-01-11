package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.entity.AIChat;
import QAEngineer.ShareMySight.model.request.AIPromptRequest;
import QAEngineer.ShareMySight.model.response.AIPromptResponse;
import QAEngineer.ShareMySight.model.response.AIUploadResponse;
import QAEngineer.ShareMySight.model.response.StandardResponse;
import QAEngineer.ShareMySight.service.serviceInteface.AIChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "app/ai-chat")
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

    @PostMapping("/upload")
    public ResponseEntity<StandardResponse<AIUploadResponse>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("text") String text
    ) {
        AIUploadResponse message = aiChatService.upload(file, text);

        StandardResponse<AIUploadResponse> response = StandardResponse.<AIUploadResponse>builder()
                .status(true)
                .message("success")
                .data(message)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StandardResponse<AIPromptResponse>> generateResponse(
            @RequestBody AIPromptRequest request
            ) {
        AIPromptResponse message = aiChatService.getAIResponse(request);

        StandardResponse<AIPromptResponse> response = StandardResponse.<AIPromptResponse>builder()
                .status(true)
                .message("success")
                .data(message)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

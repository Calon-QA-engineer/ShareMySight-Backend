package QAEngineer.ShareMySight.service.serviceImpl;

import QAEngineer.ShareMySight.entity.AIChat;
import QAEngineer.ShareMySight.entity.User;
import QAEngineer.ShareMySight.exception.CustomException;
import QAEngineer.ShareMySight.model.OpenAIRequestDTO;
import QAEngineer.ShareMySight.model.OpenAIResponseDTO;
import QAEngineer.ShareMySight.model.request.AIPromptRequest;
import QAEngineer.ShareMySight.model.response.AIPromptResponse;
import QAEngineer.ShareMySight.model.response.AIUploadResponse;
import QAEngineer.ShareMySight.repository.AIChatRepository;
import QAEngineer.ShareMySight.service.serviceInteface.AIChatService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AIChatServiceImpl implements AIChatService {
    private final AmazonS3 amazonS3;
    private final AIChatRepository aiChatRepository;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.region}")
    private String region;
    @Value("${openai.api.model}")
    private String model;
    @Value("${openai.api.key}")
    private String apiKey;
    @Value("${openai.api.url}")
    private String apiUrl;

    @Override
    public AIUploadResponse upload(MultipartFile multipartFile, String text) {
        // 1. upload the image to AWS S3
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            throw new CustomException("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 2. build the DTO response
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AIChat aiChat = AIChat.builder()
                .imageUrl(fileUrl)
                .text(text)
                .statusRecord('A')
                .user(user)
                .build();

        // 3. save to DB
        aiChatRepository.save(aiChat);

        // 4. construct DTO
        return AIUploadResponse.builder()
                .id(aiChat.getId())
                .imageUrl(aiChat.getImageUrl())
                .text(aiChat.getText())
                .build();
    }

    @Override
    public AIPromptResponse getAIResponse(AIPromptRequest request) {
        // check if the prompt id is found or not
        AIChat aiChat =  aiChatRepository.findById(request.getPromptId())
                .orElseThrow(() -> new CustomException("prompt id, " + request.getPromptId() + "not found", HttpStatus.NOT_FOUND));

        if (aiChat.getImageUrl() == null || aiChat.getImageUrl().isEmpty()) {
            throw new CustomException("imageUrl is not present", HttpStatus.BAD_REQUEST);
        }

        // setup the content
        OpenAIRequestDTO.OpenAITextContentDTO textContent = OpenAIRequestDTO.OpenAITextContentDTO.builder()
                .type("text")
                .text(aiChat.getText())
                .build();

        OpenAIRequestDTO.OpenAIImageContentDTO typeContent = OpenAIRequestDTO.OpenAIImageContentDTO.builder()
                .type("image_url")
                .imageUrl(
                        OpenAIRequestDTO.OpenAIImageUrl.builder()
                                .url(aiChat.getImageUrl())
                                .build()
                    )
                .build();

        List<Object> contents = new ArrayList<>();
        contents.add(textContent);
        contents.add(typeContent);

        // setup the message
        OpenAIRequestDTO.OpenAIMessageDTO message = OpenAIRequestDTO.OpenAIMessageDTO.builder()
                .role("user")
                .content(contents)
                .build();

        // construct the request for the model
        OpenAIRequestDTO openAIRequest = OpenAIRequestDTO.builder()
                .model(model)
                .messages(List.of(message))
                .maxTokens(100)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        HttpEntity<?> requestBody = new HttpEntity<>(openAIRequest, headers);

        OpenAIResponseDTO apiResponse = restTemplate.postForEntity(
                apiUrl,
                requestBody,
                OpenAIResponseDTO.class
        ).getBody();

        // update the description to the DB
        assert apiResponse != null;
        aiChat.setDescription(apiResponse.getDescription());
        aiChatRepository.save(aiChat);

        return AIPromptResponse.builder()
                .promptId(request.getPromptId())
                .imageUrl(aiChat.getImageUrl())
                .text(aiChat.getText())
                .description(apiResponse.getDescription())
                .build();
    }

    public List<AIChat> getChatHistory() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = user.getId();
        return aiChatRepository.findAllByUserIdExcludingFields(userId);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}

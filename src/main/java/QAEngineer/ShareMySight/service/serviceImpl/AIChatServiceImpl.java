package QAEngineer.ShareMySight.service.serviceImpl;

import QAEngineer.ShareMySight.entity.AIChat;
import QAEngineer.ShareMySight.entity.User;
import QAEngineer.ShareMySight.exception.CustomException;
import QAEngineer.ShareMySight.model.response.AIChatResponse;
import QAEngineer.ShareMySight.repository.AIChatRepository;
import QAEngineer.ShareMySight.service.serviceInteface.AIChatService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIChatServiceImpl implements AIChatService {
    private final AmazonS3 amazonS3;
    private final AIChatRepository aiChatRepository;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.region}")
    private String region;

    @Override
    public AIChatResponse upload(MultipartFile multipartFile) {
        // 1. upload the image to AWS S3
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            log.info(String.valueOf(e));
            throw new CustomException("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 2. build the DTO response
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AIChat aiChat = AIChat.builder()
                .imageUrl(fileUrl)
                .statusRecord('A')
                .user(user)
                .build();

        // 3. save to DB
        aiChatRepository.save(aiChat);

        // 4. construct DTO
        return AIChatResponse.builder()
                .id(aiChat.getId())
                .imageUrl(aiChat.getImageUrl())
                .description(aiChat.getDescription())
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

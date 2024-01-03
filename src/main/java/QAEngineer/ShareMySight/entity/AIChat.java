package QAEngineer.ShareMySight.entity;

import QAEngineer.ShareMySight.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ai_chat")
@Builder
public class AIChat {
    @Id
    @Indexed(unique=true)
    private String id;

    @Field(name = "image_url")
    private String imageUrl;

    @Field(name = "description")
    private String description;

    @DBRef
    @Field(name = "user")
    private User user;

    @Field(name = "status_record")
    private char statusRecord;

    @CreatedDate
    @Field(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private LocalDateTime updatedAt;
}

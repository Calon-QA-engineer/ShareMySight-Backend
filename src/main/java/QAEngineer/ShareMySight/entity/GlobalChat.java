package QAEngineer.ShareMySight.entity;

import QAEngineer.ShareMySight.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "global_chats")
@Builder
public class GlobalChat {
    @Id
    @Indexed(unique=true)
    private String id;

    @Field(name = "message")
    private String message;

    @Field(name = "imageUrl")
    private String imageUrl;

    @Field(name = "datetime")
    private Date datetime;

    @Field(name = "user")
    private UserDTO user;

    @Field(name = "status_record")
    private char statusRecord;

    @CreatedDate
    @Field(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private Date updatedAt;
}

package QAEngineer.ShareMySight.entity;

import QAEngineer.ShareMySight.enums.VideoCallStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "video_call_sessions")
public class VideoCallSession {
  @MongoId(FieldType.OBJECT_ID)
  @Indexed(unique=true)
  private String id;
  
  @Field(name = "socket_session_id")
  private String socketSessionId;
  
  @Field(name = "video_call_status")
  @Enumerated(value = EnumType.STRING)
  private VideoCallStatus videoCallStatus;
  
  @Field(name = "status_record")
  private char statusRecord;
  
  @CreatedDate
  @Field(name = "created_at")
  private Date createdAt;
  
  @LastModifiedDate
  @Field(name = "updated_at")
  private Date updatedAt;
}

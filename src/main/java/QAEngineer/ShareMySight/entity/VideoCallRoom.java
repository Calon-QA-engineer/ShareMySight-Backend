package QAEngineer.ShareMySight.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "video_call_rooms")
public class VideoCallRoom {
  @Id
  @Indexed(unique=true)
  private String id;
  
  @Field(name = "room_id")
  private String roomId;
  
  @Field(name = "first_participant")
  private String firstParticipant;
  
  @Field(name = "second_participant")
  private String secondParticipant;
  
  @Field(name = "status_record")
  private char statusRecord;
  
  @CreatedDate
  @Field(name = "created_at")
  private Date createdAt;
  
  @LastModifiedDate
  @Field(name = "updated_at")
  private Date updatedAt;
}

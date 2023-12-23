package QAEngineer.ShareMySight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="global_chats")
public class GlobalChat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="message_id")
    private UUID messageId;

    @Column(name="message")
    private String message;

    @Column(name="datetime")
    private Date datetime;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="status_record")
    private char statusRecord;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

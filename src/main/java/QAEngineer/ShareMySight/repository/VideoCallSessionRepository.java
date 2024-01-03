package QAEngineer.ShareMySight.repository;

import QAEngineer.ShareMySight.entity.VideoCallSession;
import QAEngineer.ShareMySight.enums.VideoCallStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoCallSessionRepository extends MongoRepository<VideoCallSession, String> {
  Optional<VideoCallSession> findBySocketSessionIdAndStatusRecord(
    String socketSessionId,
    char statusRecord
  );
  
  Optional<List<VideoCallSession>> findAllBySocketSessionIdAndVideoCallStatusAndStatusRecord(
    Iterable<String> socketSessionIds,
    VideoCallStatus videoCallStatus,
    char statusRecord
  );
}

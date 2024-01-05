package QAEngineer.ShareMySight.repository;

import QAEngineer.ShareMySight.entity.VideoCallSession;
import QAEngineer.ShareMySight.enums.VideoCallStatus;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoCallSessionRepository extends MongoRepository<VideoCallSession, String> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
  Optional<VideoCallSession> findBySocketSessionIdAndStatusRecord(
    String socketSessionId,
    char statusRecord
  );
  
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
  Optional<List<VideoCallSession>> findAllBySocketSessionIdAndVideoCallStatusAndStatusRecord(
    Iterable<String> socketSessionIds,
    VideoCallStatus videoCallStatus,
    char statusRecord
  );
  
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
  List<VideoCallSession> findAllBySocketSessionIdIn(List<String> socketSessionIds);
}

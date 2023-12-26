package QAEngineer.ShareMySight.repository;

import QAEngineer.ShareMySight.entity.VideoCallRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoCallRoomRepository extends MongoRepository<VideoCallRoom, String> {
  Optional<List<VideoCallRoom>> findByStatusRecordAndSecondParticipantIsNull(char statusRecord);
}

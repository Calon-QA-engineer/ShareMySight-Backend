package QAEngineer.ShareMySight.service.serviceInteface;

import QAEngineer.ShareMySight.entity.VideoCallSession;
import QAEngineer.ShareMySight.enums.VideoCallStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VideoCallSessionService {
  VideoCallSession find(
    String socketSessionId,
    char statusRecord
  );
  
  void add(String socketSessionId);
  
  void updateToOpenCall(String socketSessionId);
  
  void updateToOnCall(String socketSessionId);
  
  void updateToCloseCall(String socketSessionId);
  
  List<VideoCallSession> getAll(
    List<String> socketSessionIds,
    VideoCallStatus videoCallStatus,
    char statusRecord
  );
  
  void delete(String socketSessionId);
}

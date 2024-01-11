package QAEngineer.ShareMySight.service.serviceImpl;

import QAEngineer.ShareMySight.entity.VideoCallSession;
import QAEngineer.ShareMySight.enums.VideoCallStatus;
import QAEngineer.ShareMySight.repository.VideoCallSessionRepository;
import QAEngineer.ShareMySight.service.serviceInteface.VideoCallSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoCallSessionServiceImpl implements VideoCallSessionService {
  private final VideoCallSessionRepository repository;
  
  @Override
  public VideoCallSession find(String socketSessionId, char statusRecord) {
    Optional<VideoCallSession> videoCallSessionOptional = repository.findBySocketSessionIdAndStatusRecord(
      socketSessionId,
      statusRecord
    );
    return videoCallSessionOptional.orElse(null);
  }
  
  @Override
  public void add(String socketSessionId) {
    Date dateNow = new Date();
    VideoCallSession videoCallSession = VideoCallSession.builder()
      .socketSessionId(socketSessionId)
      .videoCallStatus(VideoCallStatus.CLOSE_CALL)
      .createdAt(dateNow)
      .updatedAt(dateNow)
      .statusRecord('A')
      .build();
    repository.save(videoCallSession);
  }
  
  @Override
  public void updateToOpenCall(String socketSessionId) {
    VideoCallSession videoCallSession = repository.findBySocketSessionIdAndStatusRecord(
      socketSessionId,
      'A'
    ).orElse(null);
    videoCallSession.setVideoCallStatus(VideoCallStatus.OPEN_CALL);
    repository.save(videoCallSession);
  }
  
  @Override
  public void updateToOnCall(String socketSessionId) {
    VideoCallSession videoCallSession = repository.findBySocketSessionIdAndStatusRecord(
      socketSessionId,
      'A'
    ).orElse(null);
    videoCallSession.setVideoCallStatus(VideoCallStatus.ON_CALL);
    repository.save(videoCallSession);
  }
  
  @Override
  public void updateToCloseCall(String socketSessionId) {
    VideoCallSession videoCallSession = repository.findBySocketSessionIdAndStatusRecord(
      socketSessionId,
      'A'
    ).orElse(null);
    videoCallSession.setVideoCallStatus(VideoCallStatus.CLOSE_CALL);
    repository.save(videoCallSession);
  }
  
  @Override
  public List<VideoCallSession> getAll(
    List<String> socketSessionIds,
    VideoCallStatus videoCallStatus,
    char statusRecord
  ) {
    List<VideoCallSession> videoCallSessions = repository.findAllBySocketSessionIdIn(socketSessionIds);
    if (videoCallSessions.isEmpty()) {
      return null;
    }
    return videoCallSessions.stream()
      .filter(
        videoCallSession -> videoCallSession.getVideoCallStatus() == videoCallStatus
        && videoCallSession.getStatusRecord() == statusRecord
      )
      .toList();
  }
  
  @Override
  public void delete(String socketSessionId) {
    VideoCallSession videoCallSession
      = repository.findBySocketSessionIdAndStatusRecord(socketSessionId, 'A').get();
    videoCallSession.setStatusRecord('D');
    repository.save(videoCallSession);
  }
}

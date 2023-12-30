package QAEngineer.ShareMySight.service.serviceInteface;

import QAEngineer.ShareMySight.entity.VideoCallRoom;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface VideoCallRoomService {
  VideoCallRoom getRandomRoom(HttpServletRequest httpServletRequest);
  
  void addRoom(HttpServletRequest httpServletRequest, String roomId);
  
  void deleteRoom(String id);
}

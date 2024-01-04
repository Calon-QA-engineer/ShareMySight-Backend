package QAEngineer.ShareMySight.service.serviceInteface;

import QAEngineer.ShareMySight.entity.VideoCallRoom;
import org.springframework.stereotype.Service;

@Service
public interface VideoCallRoomService {
  VideoCallRoom getRandomRoom();
  
  void addRoom(String roomId);
  
  void deleteRoom(String id);
}

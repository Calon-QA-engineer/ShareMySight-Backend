package QAEngineer.ShareMySight.service.serviceImpl;

import QAEngineer.ShareMySight.entity.VideoCallRoom;
import QAEngineer.ShareMySight.exception.CustomException;
import QAEngineer.ShareMySight.repository.VideoCallRoomRepository;
import QAEngineer.ShareMySight.security.JwtTokenUtil;
import QAEngineer.ShareMySight.service.serviceInteface.VideoCallRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoCallRoomServiceImpl implements VideoCallRoomService {
  private final VideoCallRoomRepository repository;
  private final JwtTokenUtil jwtTokenUtil;
  
  @Override
  public VideoCallRoom getRandomRoom() {
    List<VideoCallRoom> foundRooms = repository.findByStatusRecordAndSecondParticipantIsNull('A')
      .orElseThrow(() -> new CustomException("Rooms not found", HttpStatus.NOT_FOUND));
    
    if (foundRooms.isEmpty()) {
      throw new CustomException("Rooms not found", HttpStatus.NOT_FOUND);
    }
    
    // Mengacak urutan elemen dalam daftar
    Collections.shuffle(foundRooms);
    
    // Mengambil elemen pertama setelah diacak
    VideoCallRoom randomRoom = foundRooms.get(0);
    
    UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = principal.getUsername();
    
    randomRoom.setSecondParticipant(username);
    randomRoom.setUpdatedAt(new Date());
    
    repository.save(randomRoom);
    return randomRoom;
  }
  
  @Override
  public void addRoom(String roomId) {
    UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = principal.getUsername();
    
    VideoCallRoom newRoom = VideoCallRoom.builder()
      .roomId(roomId)
      .firstParticipant(username)
      .secondParticipant(null)
      .statusRecord('A')
      .createdAt(new Date())
      .updatedAt(new Date())
      .build();
    
    repository.save(newRoom);
  }
  
  @Override
  public void deleteRoom(String id) {
    VideoCallRoom foundRoom = repository.findById(id)
      .orElseThrow(() -> new CustomException("Room given id: " + id + " not found", HttpStatus.NOT_FOUND));
    
    foundRoom.setStatusRecord('D');
    repository.save(foundRoom);
  }
}

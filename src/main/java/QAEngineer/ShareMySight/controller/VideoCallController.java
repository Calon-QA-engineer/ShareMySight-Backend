package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.entity.VideoCallRoom;
import QAEngineer.ShareMySight.model.request.AddVideoCallRoomRequest;
import QAEngineer.ShareMySight.model.response.StandardResponse;
import QAEngineer.ShareMySight.service.serviceInteface.VideoCallRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Video Call")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "video-call")
public class VideoCallController {
  private final VideoCallRoomService videoCallRoomService;
  
  @Operation(summary = "Get Random Video Call Room")
  @GetMapping
  public ResponseEntity<StandardResponse<Object>> get() {
    VideoCallRoom randomRoom = videoCallRoomService.getRandomRoom();
    StandardResponse<Object> standardResponse = StandardResponse.builder()
      .status(true)
      .message("Waiting for other to join...")
      .data(randomRoom)
      .build();
    return new ResponseEntity<>(standardResponse, HttpStatus.OK);
  }
  
  @Operation(summary = "Add Generated Video Call Room")
  @PostMapping
  public ResponseEntity<StandardResponse<Object>> add(
    @RequestBody @Valid AddVideoCallRoomRequest addVideoCallRoomRequest
  ) {
    videoCallRoomService.addRoom(addVideoCallRoomRequest.getRoomId());
    StandardResponse<Object> standardResponse = StandardResponse.builder()
      .status(true)
      .message("Waiting for other to join...")
      .data(null)
      .build();
    return new ResponseEntity<>(standardResponse, HttpStatus.CREATED);
  }
  
  @Operation(summary = "Delete Video Call Room")
  @DeleteMapping("{id}")
  public ResponseEntity<StandardResponse<Object>> delete(
    @PathVariable String id
  ) {
    videoCallRoomService.deleteRoom(id);
    StandardResponse<Object> standardResponse = StandardResponse.builder()
      .status(true)
      .message("Waiting for other to join...")
      .data(null)
      .build();
    return new ResponseEntity<>(standardResponse, HttpStatus.OK);
  }
}

package QAEngineer.ShareMySight.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/socket.io")
public class SocketIOEndpointController {
  
  private final SocketIOController socketIOController;
  
  public SocketIOEndpointController(SocketIOController socketIOController) {
    this.socketIOController = socketIOController;
  }
  
  // Map GET request for "/socket.io" to handle Socket.IO events
  @GetMapping
  public ResponseEntity<String> handleSocketIORequest() {
    // Call the methods from SocketIOController to handle Socket.IO events
    socketIOController.onConnected();
    socketIOController.onDisconnected();
    // ... Call other methods similarly
    socketIOController.onAnswerCall();
    socketIOController.onCallRandom();
    socketIOController.onEndCall();
    socketIOController.onCancelCall();
    socketIOController.onGoingRandomCall();
    socketIOController.onCallUser();
    // Return appropriate response
    return ResponseEntity.ok("Socket.IO events handled successfully!");
  }
}


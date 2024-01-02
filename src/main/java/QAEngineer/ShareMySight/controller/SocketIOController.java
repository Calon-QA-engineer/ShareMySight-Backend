package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.model.request.CallUserRequest;
import QAEngineer.ShareMySight.model.response.CallUserResponse;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class SocketIOController {
  private final SocketIOServer server;
  
  public SocketIOController(SocketIOServer server) {
    this.server = server;
    server.addConnectListener(onConnected());
    server.addDisconnectListener(onDisconnected());
    server.addEventListener("callUser", CallUserRequest.class, onCallUser());
  }
  
  private ConnectListener onConnected() {
    return socketIOClient -> {
      log.info("Socket ID[{}] Connected to socket", socketIOClient.getSessionId().toString());
      socketIOClient.sendEvent("me", socketIOClient.getSessionId().toString());
    };
  }
  
  private DisconnectListener onDisconnected() {
    return socketIOClient -> {
      log.info("Client[{}] - Disconnected from socket", socketIOClient.getSessionId().toString());
      socketIOClient.getNamespace().getBroadcastOperations().sendEvent("callEnded");
    };
  }
  
  private DataListener<CallUserRequest> onCallUser() {
    return (socketIOClient, callUserRequest, ackRequest) -> {
      log.info("Call User[{}]", socketIOClient.getSessionId().toString());
      log.info("Call User data >>> {}", callUserRequest);
      SocketIOClient targetedClient = server.getClient(UUID.fromString(callUserRequest.getUserToCall()));
      CallUserResponse callUserResponse = CallUserResponse.builder()
        .from(callUserRequest.getFrom())
        .name(callUserRequest.getName())
        .signal(callUserRequest.getSignalData())
        .build();
      targetedClient.sendEvent(
        "callUser",
          callUserResponse
        );
    };
  }
}

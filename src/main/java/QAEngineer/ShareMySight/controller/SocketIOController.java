package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.model.CallUserDto;
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
    server.addEventListener("callUser", CallUserDto.class, onCallUser());
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
  
  private DataListener<CallUserDto> onCallUser() {
    return (socketIOClient, callUserDto, ackRequest) -> {
      log.info("Call User[{}]", socketIOClient.getSessionId().toString());
      log.info("Call User data >>> {}", callUserDto);
      SocketIOClient targetedClient = server.getClient(UUID.fromString(callUserDto.getUserToCall()));
      targetedClient.sendEvent("callUser", callUserDto);
    };
  }
}

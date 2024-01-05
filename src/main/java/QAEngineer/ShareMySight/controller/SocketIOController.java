package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.entity.VideoCallSession;
import QAEngineer.ShareMySight.enums.VideoCallStatus;
import QAEngineer.ShareMySight.model.request.AnswerCallRequest;
import QAEngineer.ShareMySight.model.request.CallUserRequest;
import QAEngineer.ShareMySight.model.request.RandomCallUserRequest;
import QAEngineer.ShareMySight.model.response.CallUserResponse;
import QAEngineer.ShareMySight.service.serviceInteface.VideoCallSessionService;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class SocketIOController {
  private final SocketIOServer server;
  private final VideoCallSessionService videoCallSessionService;
  
  public SocketIOController(
    SocketIOServer server,
    VideoCallSessionService videoCallSessionService
  ) {
    this.server = server;
    this.videoCallSessionService = videoCallSessionService;
    server.addConnectListener(onConnected());
    server.addDisconnectListener(onDisconnected());
    server.addEventListener("callUser", CallUserRequest.class, onCallUser());
    server.addEventListener("answerCall", AnswerCallRequest.class, onAnswerCall());
    server.addEventListener("startRandomCall", RandomCallUserRequest.class, onCallRandom());
    server.addEventListener("goingRandomCall", AnswerCallRequest.class, onGoingRandomCall());
    server.addEventListener("endCall", AnswerCallRequest.class, onEndCall());
    server.addEventListener("cancelCall", RandomCallUserRequest.class, onCancelCall());
  }
  
  private ConnectListener onConnected() {
    return socketIOClient -> {
      socketIOClient.sendEvent("me", socketIOClient.getSessionId().toString());
      
      String mySocketSessionId = socketIOClient.getSessionId().toString();
      VideoCallSession myVideoCallSession = videoCallSessionService.find(
        mySocketSessionId,
        'A'
      );
      
      if (myVideoCallSession == null) {
        videoCallSessionService.add(mySocketSessionId);
      }
    };
  }
  
  private DisconnectListener onDisconnected() {
    return socketIOClient -> {
      String mySocketSessionId = socketIOClient.getSessionId().toString();
      socketIOClient.getNamespace().getBroadcastOperations().sendEvent("callEnded");
      videoCallSessionService.delete(mySocketSessionId);
    };
  }
  
  private DataListener<CallUserRequest> onCallUser() {
    return (socketIOClient, callUserRequest, ackRequest) -> {
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
  
  private DataListener<AnswerCallRequest> onAnswerCall() {
    return (socketIOClient, answerCallRequest, ackRequest) -> {
      SocketIOClient targetedClient = server.getClient(UUID.fromString(answerCallRequest.getTo()));
      targetedClient.sendEvent("callAccepted", answerCallRequest.getSignal());
    };
  }
  
  private DataListener<RandomCallUserRequest> onCallRandom() {
    return (socketIOClient, randomCallUserRequest, ackRequest) -> {
      String mySocketSessionId = socketIOClient.getSessionId().toString();
      
      videoCallSessionService.updateToOpenCall(mySocketSessionId);
      
      List<SocketIOClient> otherClients = server.getAllClients()
        .stream()
        .filter(
          (client) -> !Objects.equals(client.getSessionId().toString(), mySocketSessionId)
        )
        .toList();
      if (!otherClients.isEmpty()) {
        List<String> socketSessionIds = otherClients.stream()
          .map(client -> client.getSessionId().toString())
          .toList();
        List<VideoCallSession> videoCallSessions = videoCallSessionService.getAll(
          socketSessionIds,
          VideoCallStatus.OPEN_CALL,
          'A'
        );
        if (!videoCallSessions.isEmpty()) {
          Collections.shuffle(videoCallSessions);
          VideoCallSession videoCallSession = videoCallSessions.get(0);
          log.info("target socketSessionId >>>>>>>> {}", videoCallSession.getSocketSessionId());
          
          SocketIOClient targetedClient = server.getClient(UUID.fromString(videoCallSession.getSocketSessionId()));
          log.info("randomCallUserRequest.getSignalData >>>> {}", randomCallUserRequest.getSignalData());
          CallUserResponse callUserResponse = CallUserResponse.builder()
            .from(randomCallUserRequest.getFrom())
            .name(randomCallUserRequest.getName())
            .signal(randomCallUserRequest.getSignalData())
            .build();
          targetedClient.sendEvent("startRandomCall", callUserResponse);
        }
      }
    };
  }
  
  private DataListener<AnswerCallRequest> onGoingRandomCall() {
    return (client, data, ackSender) -> {
      SocketIOClient targetedClient = server.getClient(UUID.fromString(data.getTo()));
      videoCallSessionService.updateToOnCall(targetedClient.getSessionId().toString());
      targetedClient.sendEvent("callAccepted", data.getSignal());
    };
  }
  
  private DataListener<AnswerCallRequest> onEndCall() {
    return (client, data, ackSender) -> {
      videoCallSessionService.updateToCloseCall(client.getSessionId().toString());
      videoCallSessionService.updateToCloseCall(data.getTo());
      SocketIOClient targetedClient = server.getClient(UUID.fromString(data.getTo()));
      targetedClient.sendEvent("endCall");
    };
  }
  
  private DataListener<RandomCallUserRequest> onCancelCall() {
    return (client, data, ackSender) -> {
      videoCallSessionService.updateToCloseCall(client.getSessionId().toString());
    };
  }
}

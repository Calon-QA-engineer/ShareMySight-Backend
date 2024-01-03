package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.entity.VideoCallSession;
import QAEngineer.ShareMySight.repository.VideoCallSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {
  private final VideoCallSessionRepository videoCallSessionRepository;
  @GetMapping(value = "/test")
  private void test() {
    String[] socketSessionIds = {"a3f5ad5f-c667-45bf-b25e-f32bdd08ad9d",
      "a3f5ad5f-c667-45bf-b25e-f32bdd08ad9d",
      "a3f5ad5f-c667-45bf-b25e-f32bdd08ad9d"};
    
    List<VideoCallSession> allBySocketSessionId =
      videoCallSessionRepository.findAllBySocketSessionIdIn(List.of(socketSessionIds));
    log.info(">>>>>>>>>>> {}", allBySocketSessionId);
  }
  
}

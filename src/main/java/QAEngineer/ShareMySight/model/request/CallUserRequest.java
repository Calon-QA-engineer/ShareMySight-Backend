package QAEngineer.ShareMySight.model.request;

import lombok.*;

@Getter
public class CallUserRequest {
  private String userToCall;
  private Object signalData;
  private String from;
  private String name;
}

package QAEngineer.ShareMySight.model.request;

import QAEngineer.ShareMySight.model.SignalDataDto;
import lombok.*;

@Getter
public class CallUserRequest {
  private String userToCall;
  private SignalDataDto signalData;
  private String from;
  private String name;
}

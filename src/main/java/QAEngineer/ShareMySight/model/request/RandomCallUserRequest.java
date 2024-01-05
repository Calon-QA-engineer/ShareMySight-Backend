package QAEngineer.ShareMySight.model.request;

import QAEngineer.ShareMySight.model.SignalDataDto;
import lombok.Getter;

@Getter
public class RandomCallUserRequest {
  private SignalDataDto signalData;
  private String from;
  private String name;
}

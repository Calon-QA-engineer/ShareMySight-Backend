package QAEngineer.ShareMySight.model.response;

import QAEngineer.ShareMySight.model.SignalDataDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CallUserResponse {
  private SignalDataDto signal;
  private String from;
  private String name;
}

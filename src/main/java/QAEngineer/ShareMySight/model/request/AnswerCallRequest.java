package QAEngineer.ShareMySight.model.request;

import QAEngineer.ShareMySight.model.SignalDataDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AnswerCallRequest {
  private String to;
  private SignalDataDto signal;
}

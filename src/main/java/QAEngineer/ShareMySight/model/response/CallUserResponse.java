package QAEngineer.ShareMySight.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CallUserResponse {
  private Object signal;
  private String from;
  private String name;
}

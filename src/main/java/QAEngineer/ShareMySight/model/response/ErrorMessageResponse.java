package QAEngineer.ShareMySight.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorMessageResponse {
  private String message;
}

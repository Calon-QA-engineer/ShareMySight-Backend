package QAEngineer.ShareMySight.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignalDataDto {
  private String type;
  private String sdp;
}

package QAEngineer.ShareMySight.model;

import lombok.*;

@Data
public class CallUserDto {
  private String userToCall;
  private Object signalData;
  private String from;
  private String name;
}

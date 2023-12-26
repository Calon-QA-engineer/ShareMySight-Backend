package QAEngineer.ShareMySight.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddVideoCallRoomRequest {
  @NotEmpty(message = "Room ID is required")
  @NotNull(message = "Room ID is required")
  @NotBlank(message = "Room ID is required")
  String roomId;
}

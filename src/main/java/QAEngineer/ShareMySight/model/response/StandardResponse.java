package QAEngineer.ShareMySight.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponse <T> {
    private boolean status; // t or f berdasarkan resopnse berhasil/tidak
    private Integer code; // status code
    private String message;
    private T data; // DTO object
}

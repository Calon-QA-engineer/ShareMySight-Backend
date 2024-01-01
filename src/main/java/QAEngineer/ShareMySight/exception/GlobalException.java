package QAEngineer.ShareMySight.exception;

import QAEngineer.ShareMySight.model.response.ErrorMessageResponse;
import QAEngineer.ShareMySight.model.response.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<StandardResponse<Object>> handleCustomException(CustomException ex) {
        StandardResponse<Object> response = StandardResponse.<Object>builder()
                .status(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getCode().value()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        StandardResponse<Object> response = StandardResponse.<Object>builder()
                .status(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.valueOf(400));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        StandardResponse<Object> response = StandardResponse.<Object>builder()
                .status(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.valueOf(400));
    }
    
    // Setiap argument yang ada di body json jika terkena validasi, maka semuanya dilempar balik dengan mapping
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errorMap);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessageResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(ErrorMessageResponse.builder().message("Invalid email or password").build());
    }
    
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorMessageResponse> handleDisabledException(DisabledException ex) {
        return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body(ErrorMessageResponse.builder().message(ex.getMessage()).build());
    }
}

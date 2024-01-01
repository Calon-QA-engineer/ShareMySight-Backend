package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.model.response.AuthenticationResponse;
import QAEngineer.ShareMySight.model.request.LoginRequest;
import QAEngineer.ShareMySight.model.request.RegisterRequest;
import QAEngineer.ShareMySight.model.response.StandardResponse;
import QAEngineer.ShareMySight.service.serviceImpl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthServiceImpl authService;

    @Operation(summary = "Register")
    @PostMapping("/register")
    public ResponseEntity<StandardResponse<Object>> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        StandardResponse<Object> response = StandardResponse.<Object>builder()
                .status(true)
                .message("user registered")
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<StandardResponse<AuthenticationResponse>> login(@RequestBody @Valid LoginRequest request) {
        AuthenticationResponse authToken = authService.login(request);
        StandardResponse<AuthenticationResponse> response = StandardResponse.<AuthenticationResponse>builder()
                .status(true)
                .message("authenticated")
                .data(authToken)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

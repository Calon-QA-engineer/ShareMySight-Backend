package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.model.AuthenticationResponse;
import QAEngineer.ShareMySight.model.LoginRequest;
import QAEngineer.ShareMySight.model.RegisterRequest;
import QAEngineer.ShareMySight.model.StandardResponse;
import QAEngineer.ShareMySight.service.serviceImpl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<StandardResponse<Object>> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        StandardResponse<Object> response = StandardResponse.<Object>builder()
                .status(true)
                .code(201)
                .message("user registered")
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<AuthenticationResponse>> login(@RequestBody LoginRequest request) {
        AuthenticationResponse authToken = authService.login(request);
        StandardResponse<AuthenticationResponse> response = StandardResponse.<AuthenticationResponse>builder()
                .status(true)
                .code(200)
                .message("authenticated")
                .data(authToken)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

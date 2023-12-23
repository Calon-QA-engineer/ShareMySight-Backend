package QAEngineer.ShareMySight.service.serviceImpl;

import QAEngineer.ShareMySight.entity.User;
import QAEngineer.ShareMySight.exception.CustomException;
import QAEngineer.ShareMySight.model.AuthenticationResponse;
import QAEngineer.ShareMySight.model.LoginRequest;
import QAEngineer.ShareMySight.model.RegisterRequest;
import QAEngineer.ShareMySight.repository.UserRepository;
import QAEngineer.ShareMySight.security.JwtTokenUtil;
import QAEngineer.ShareMySight.service.serviceInteface.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void register(RegisterRequest request) {
        String email = request.getEmail();

        // Check if any required field is empty or null
        if (isEmpty(request.getEmail()) || isEmpty(request.getPassword())
                || isEmpty(request.getLanguageId()) || isEmpty(request.getRole())) {
            throw new CustomException("All user fields are required", 400);
        }

        // if user already registered, it is an error
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User already registered");
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .statusRecord('A').build();

        userRepository.save(newUser);
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new CustomException("User not registered yet", 400);
        }

        String jwtToken = jwtTokenUtil.generateToken(user.get());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private <T> boolean isEmpty(T value) {
        if (value instanceof String) {
            return ((String) value).trim().isEmpty();
        }
        return value == null;
    }

}
package QAEngineer.ShareMySight.service.serviceInteface;

import QAEngineer.ShareMySight.model.AuthenticationResponse;
import QAEngineer.ShareMySight.model.LoginRequest;
import QAEngineer.ShareMySight.model.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    AuthenticationResponse login(LoginRequest request);
}

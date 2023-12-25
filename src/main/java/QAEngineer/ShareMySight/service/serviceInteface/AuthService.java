package QAEngineer.ShareMySight.service.serviceInteface;

import QAEngineer.ShareMySight.model.response.AuthenticationResponse;
import QAEngineer.ShareMySight.model.request.LoginRequest;
import QAEngineer.ShareMySight.model.request.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    AuthenticationResponse login(LoginRequest request);
}

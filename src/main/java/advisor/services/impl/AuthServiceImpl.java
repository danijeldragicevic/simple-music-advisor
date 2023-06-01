package advisor.services.impl;

import advisor.repositories.IAuthRepository;
import advisor.repositories.impl.AuthRepositoryImpl;
import advisor.services.IAuthService;
import advisor.utils.LocalhostServer;

import java.net.http.HttpRequest;

public class AuthServiceImpl implements IAuthService {
    private final IAuthRepository authRepository = new AuthRepositoryImpl();
    
    @Override
    public String getAccessToken() throws RuntimeException {
        authRepository.printAuthURL();
        String authCode = authRepository.getAuthenticationCode(LocalhostServer.initAndStart());
        HttpRequest authReq = authRepository.createAuthenticationReq(authCode);
        
        return authRepository.getAccessToken(authReq);
    }
}

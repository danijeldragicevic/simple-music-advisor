package advisor.services.impl;

import advisor.repositories.IExternalApiRepository;
import advisor.repositories.impl.ExternalApiRepositoryImpl;
import advisor.services.IAuthService;
import advisor.utils.LocalhostServer;

import java.net.http.HttpRequest;

public class AuthServiceImpl implements IAuthService {
    private final IExternalApiRepository repository = new ExternalApiRepositoryImpl();
    
    @Override
    public String getAccessToken() throws RuntimeException {
        repository.printAuthURL();
        String authCode = repository.getAuthenticationCode(LocalhostServer.initAndStart());
        HttpRequest authReq = repository.createAuthenticationReq(authCode);
        
        return repository.getAccessToken(authReq);
    }
}

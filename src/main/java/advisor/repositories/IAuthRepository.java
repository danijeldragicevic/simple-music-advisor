package advisor.repositories;

import com.sun.net.httpserver.HttpServer;

import java.net.http.HttpRequest;

public interface IAuthRepository {
    int HTTP_OK = 200;
    void printAuthURL();
    
    String getAuthCode(HttpServer httpServer);
    HttpRequest createAuthenticationReq(String authCode);
    
    String getAccessToken(HttpRequest request);
    HttpRequest createAuthorizationReq(String accessToken, String apiPath);
}

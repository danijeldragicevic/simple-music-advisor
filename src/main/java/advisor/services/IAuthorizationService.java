package advisor.services;

import com.sun.net.httpserver.HttpServer;

import java.net.http.HttpRequest;

public interface IAuthorizationService {
    int HTTP_OK = 200;
    void printAuthURL();
    String getAuthCode(HttpServer httpServer);
    HttpRequest createAuthReq(String authCode);
    String getAccessToken(HttpRequest request);
}

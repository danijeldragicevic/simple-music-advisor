package advisor.repositories;

import com.sun.net.httpserver.HttpServer;

import java.net.http.HttpRequest;

public interface IExternalApiRepository {
    int HTTP_OK = 200;
    void printAuthURL();
    
    String getAuthenticationCode(HttpServer httpServer);
    HttpRequest createAuthenticationReq(String authCode);

    String getAccessToken(HttpRequest request);
    HttpRequest createAuthorizationReq(String accessToken, String apiPath);

    String getResource(HttpRequest request);
    String getAllAlbums(HttpRequest request);
    String getAllCategories(HttpRequest request);
    String getAllPlaylists(HttpRequest request);
}

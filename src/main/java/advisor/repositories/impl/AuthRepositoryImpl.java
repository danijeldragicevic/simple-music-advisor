package advisor.repositories.impl;

import advisor.repositories.IAuthRepository;
import advisor.config.SpotifyApiConfig;
import advisor.utils.ConsoleOutput;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthRepositoryImpl implements IAuthRepository {
    
    @Override
    public void printAuthURL() {
        System.out.println(ConsoleOutput.REQUEST_THE_ACCESS_CODE);
        System.out.println(
                SpotifyApiConfig.AUTH_SERVER_PATH + "/authorize" + "?client_id=" + 
                SpotifyApiConfig.CLIENT_ID + "&redirect_uri=" + 
                SpotifyApiConfig.REDIRECT_URI + "&response_type=" + 
                SpotifyApiConfig.RESPONSE_TYPE_CODE
        );
    }

    @Override
    public String getAuthCode(HttpServer server) {
        final String[] authCode = new String[1];
        try {
            server.createContext("/",
                    exchange -> {
                        String query = exchange.getRequestURI().getQuery();
                        String request = "";
                        if (query != null && query.contains(SpotifyApiConfig.RESPONSE_TYPE_CODE)) {
                            authCode[0] = query.substring(5);
                            System.out.println(ConsoleOutput.CODE_RECEIVED);
                            request = ConsoleOutput.GOT_THE_CODE;
                        } else {
                            request = ConsoleOutput.CODE_NOT_FOUND;
                        }
                        exchange.sendResponseHeaders(HTTP_OK, request.length());
                        exchange.getResponseBody().write(request.getBytes());
                        exchange.getResponseBody().close();
                    });

            System.out.println(ConsoleOutput.WAITING_FOR_CODE);
            while (authCode[0] == null) {
                Thread.sleep(10);
            }
            server.stop(5);
            
        } catch (InterruptedException e) {
            System.out.println(ConsoleOutput.SERVER_ERROR);
        }
        
        return authCode[0];
    }

    @Override
    public HttpRequest createAuthenticationReq(String authCode) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(SpotifyApiConfig.AUTH_SERVER_PATH + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(""
                        + "grant_type=" + SpotifyApiConfig.GRANT_TYPE
                        + "&code=" + authCode
                        + "&client_id=" + SpotifyApiConfig.CLIENT_ID
                        + "&client_secret=" + SpotifyApiConfig.CLIENT_SECRET
                        + "&redirect_uri=" + SpotifyApiConfig.REDIRECT_URI
                ))
                .build();

        return request;
    }

    @Override
    public String getAccessToken(HttpRequest request) {
        String accessToken = null;
        System.out.println(ConsoleOutput.MAKING_HTTP_REQ);
        System.out.println(ConsoleOutput.RESPONSE);
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            accessToken = jsonObject.get("access_token").getAsString();
            
            assert accessToken != null;
            System.out.println(response.body());
            System.out.println(ConsoleOutput.SUCCESS);
        } catch (InterruptedException | IOException e) { 
            System.out.println(ConsoleOutput.ERROR_RESPONSE); 
        }
        
        return accessToken;
    }

    @Override
    public HttpRequest createAuthorizationReq(String accessToken, String apiPath) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(apiPath))
                .GET()
                .build();
        
        return request;
    }
}
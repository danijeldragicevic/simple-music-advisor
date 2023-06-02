package advisor.repositories.impl;

import advisor.config.ExternalApiConfig;
import advisor.repositories.IExternalApiRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExternalApiRepositoryImpl implements IExternalApiRepository {
    @Override
    public void printAuthURL() {
        System.out.println("use this link to request the access code:");
        System.out.println(
                ExternalApiConfig.AUTH_SERVER_PATH + "/authorize" + "?client_id=" +
                        ExternalApiConfig.CLIENT_ID + "&redirect_uri=" +
                        ExternalApiConfig.REDIRECT_URI + "&response_type=" +
                        ExternalApiConfig.RESPONSE_TYPE_CODE
        );
    }

    @Override
    public String getAuthenticationCode(HttpServer server) {
        final String[] authCode = new String[1];
        try {
            server.createContext("/",
                    exchange -> {
                        String query = exchange.getRequestURI().getQuery();
                        String request = "";
                        if (query != null && query.contains(ExternalApiConfig.RESPONSE_TYPE_CODE)) {
                            authCode[0] = query.substring(5);
                            System.out.println("code received");
                            request = "Got the code, return back to your program.";
                        } else {
                            request = "Authorization code not found. Please try again.";
                        }
                        exchange.sendResponseHeaders(HTTP_OK, request.length());
                        exchange.getResponseBody().write(request.getBytes());
                        exchange.getResponseBody().close();
                    });

            System.out.println("waiting for code...");
            while (authCode[0] == null) {
                Thread.sleep(10);
            }
            server.stop(5);

        } catch (InterruptedException e) {
            System.out.println("Server error!\n");
        }

        return authCode[0];
    }

    @Override
    public HttpRequest createAuthenticationReq(String authCode) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(ExternalApiConfig.AUTH_SERVER_PATH + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(""
                        + "grant_type=" + ExternalApiConfig.GRANT_TYPE
                        + "&code=" + authCode
                        + "&client_id=" + ExternalApiConfig.CLIENT_ID
                        + "&client_secret=" + ExternalApiConfig.CLIENT_SECRET
                        + "&redirect_uri=" + ExternalApiConfig.REDIRECT_URI
                ))
                .build();

        return request;
    }

    @Override
    public String getAccessToken(HttpRequest request) {
        String accessToken = null;
        System.out.println("Making http request for access_token...");
        System.out.println("response:");
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            accessToken = jsonObject.get("access_token").getAsString();

            assert accessToken != null;
            System.out.println(response.body());
            System.out.println("Success!\n");
        } catch (InterruptedException | IOException e) {
            System.out.println("Error response!\n");
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

    @Override
    public String getResource(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException | IOException e) {
            return "Error response";
        }
    }
}

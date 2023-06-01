package advisor.config;

public class ExternalApiConfig {
    public static String AUTH_SERVER_PATH = "https://accounts.spotify.com";
    public static String API_SERVER_PATH = "https://api.spotify.com";
    public static final String REDIRECT_URI = "http://localhost:8080";
    public static final String CLIENT_ID = "6c4fbf0c811242f0bd6c40f85bcbba17";
    public static final String CLIENT_SECRET = "5f65182046db4cf2b6b06a26d04f8c1e";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String RESPONSE_TYPE_CODE = "code";
    public static final String NEW_RELEASES_PATH = "/v1/browse/new-releases";
}
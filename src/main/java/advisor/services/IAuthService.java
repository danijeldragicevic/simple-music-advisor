package advisor.services;

public interface IAuthService {
    String getAccessToken() throws RuntimeException;
}

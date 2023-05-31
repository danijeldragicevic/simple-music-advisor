package advisor.controllers.impl;

import advisor.controllers.IMenuController;
import advisor.models.Album;
import advisor.repositories.IAlbumRepository;
import advisor.repositories.IAuthRepository;
import advisor.repositories.ICategoryRepository;
import advisor.repositories.IPlaylistRepository;
import advisor.repositories.impl.AlbumRepositoryImpl;
import advisor.repositories.impl.AuthRepositoryImpl;
import advisor.repositories.impl.CategoryRepositoryImpl;
import advisor.repositories.impl.PlaylistRepositoryImpl;
import advisor.config.SpotifyApiConfig;
import advisor.utils.ConsoleOutput;
import advisor.utils.InputScanner;
import advisor.utils.LocalhostServer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuControllerImpl implements IMenuController {
    private static final int ONE = 1;
    private static final int FIRST_INPUT_VALUE = 0;
    private static final int SECOND_INPUT_VALUE = 1;
    
    private final IAuthRepository authRepository = new AuthRepositoryImpl();
    private final IAlbumRepository albumRepository = new AlbumRepositoryImpl();
    private final IPlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
    private final ICategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private String accessToken;

    @Override
    public void showAuthMenu() {
        boolean exit = false;
        while (!exit) {
            String[] choice = InputScanner.getStringInput();
            switch (choice[FIRST_INPUT_VALUE]) {
                case "auth":
                    try {
                        authenticateApp();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    showMainMenu();
                    break;
                case "exit":
                    showGoodbyeMessage();
                    break;
                default:
                    System.out.println(ConsoleOutput.PROVIDE_ACCESS);
            }
        }
    }

    @Override
    public void authenticateApp() throws IOException {
        authRepository.printAuthURL();
        String authCode = authRepository.getAuthCode(LocalhostServer.initAndStart());
        HttpRequest authRequest = authRepository.createAuthenticationReq(authCode);
        accessToken = authRepository.getAccessToken(authRequest);
    }

    @Override
    public void showMainMenu() {
        while(true) {
            String[] choice = InputScanner.getStringInput();
            switch (choice[FIRST_INPUT_VALUE]) {
                case "new":
                    showNewReleases();
                    break;
                case "featured":
                    showFeaturedPlayLists();
                    break;
                case "categories":
                    showCategories();
                    break;
                case "playlists":
                    if (choice.length > ONE) {
                        showCategorizedPlaylists(choice[SECOND_INPUT_VALUE]);
                    } else {
                        System.out.println(ConsoleOutput.ADD_CATEGORY_NAME);
                    }
                    break;
                case "exit":
                    showGoodbyeMessage();
                    break;
                default:
                    System.out.println(ConsoleOutput.VALUE_NOT_SUPPORTED);
            }
        }
    }

    @Override
    public void showNewReleases() {
        System.out.println(ConsoleOutput.NEW_RELEASES);
        HttpRequest request = authRepository.createAuthorizationReq(accessToken, SpotifyApiConfig.API_SERVER_PATH + "/v1/browse/new-releases");
        String response = albumRepository.getNewReleases(request);

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject albums = jsonObject.getAsJsonObject("albums");
        
        List<Album> newReleases = new ArrayList<>();
        for (JsonElement i: albums.getAsJsonArray("items")) {
            Album album = new Album();
            album.setName(i.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            
            List<String> artists = new ArrayList<>();
            for (JsonElement a: i.getAsJsonObject().getAsJsonArray("artists")) {
                artists.add(a.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            }
            album.setArtists(artists);
            album.setExternalUrl(i.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").toString().replaceAll("\"", ""));
            
            newReleases.add(album);
        }
        
        for (Album a: newReleases) {
            System.out.println(a);
        }
    }
    
    @Override
    public void showFeaturedPlayLists() {
        System.out.println(ConsoleOutput.FEATURED);
        playlistRepository.getAll().forEach(
              playlist -> System.out.println(playlist.getName())  
        );
    }
    
    @Override
    public void showCategories() {
        System.out.println(ConsoleOutput.CATEGORIES);
        categoryRepository.getAll().forEach(
                category -> System.out.println(category.getName())
        );
    }

    @Override
    public void showCategorizedPlaylists(String categoryName) {
        ConsoleOutput consoleOutput = new ConsoleOutput(categoryName);
        System.out.println(consoleOutput.PLAYLISTS);
        
        playlistRepository.getAllByCategoryName(categoryName).forEach(
                category -> System.out.println(category.getName())
        );
    }

    @Override
    public void showGoodbyeMessage() {
        System.out.println(ConsoleOutput.GOODBYE);
        System.exit(0);
    }
}

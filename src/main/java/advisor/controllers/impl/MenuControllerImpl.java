package advisor.controllers.impl;

import advisor.controllers.IMenuController;
import advisor.models.Album;
import advisor.repositories.IAlbumRepository;
import advisor.repositories.ICategoryRepository;
import advisor.repositories.IPlaylistRepository;
import advisor.repositories.impl.AlbumRepositoryImpl;
import advisor.repositories.impl.CategoryRepositoryImpl;
import advisor.repositories.impl.PlaylistRepositoryImpl;
import advisor.config.ExternalApiConfig;
import advisor.services.IAlbumService;
import advisor.services.IAuthService;
import advisor.services.impl.AlbumServiceImpl;
import advisor.services.impl.AuthServiceImpl;
import advisor.utils.ConsoleOutput;
import advisor.utils.InputScanner;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class MenuControllerImpl implements IMenuController {
    private static final int FIRST_INPUT_VALUE = 0;
    private static final int SECOND_INPUT_VALUE = 1;
    
    private static final IAuthService authService = new AuthServiceImpl();
    private static final IAlbumService albumService = new AlbumServiceImpl();
    private static final IPlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
    private static final ICategoryRepository categoryRepository = new CategoryRepositoryImpl();

    private static String accessToken;
    
    @Override
    public void showAuthMenu() {
        boolean exit = false;
        while (!exit) {
            String[] choice = InputScanner.getStringInput();
            switch (choice[FIRST_INPUT_VALUE]) {
                case "auth":
                    accessToken = authService.getAccessToken();
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
                    if (choice.length > 1) {
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
        List<Album> newReleases = albumService.getNewReleases(accessToken);
        for (Album album: newReleases) {
            System.out.println(album);
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

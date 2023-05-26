package advisor.controllers.impl;

import advisor.controllers.IMenuController;
import advisor.services.IAlbumService;
import advisor.services.IAuthorizationService;
import advisor.services.ICategoryService;
import advisor.services.IPlaylistService;
import advisor.services.impl.AlbumServiceImpl;
import advisor.services.impl.AuthorizationServiceImpl;
import advisor.services.impl.CategoryServiceImpl;
import advisor.services.impl.PlaylistServiceImpl;
import advisor.utils.ConsoleOutput;
import advisor.utils.InputScanner;
import advisor.utils.LocalhostServer;

import java.io.IOException;
import java.net.http.HttpRequest;

public class MenuControllerImpl implements IMenuController {
    private static final int ONE = 1;
    private static final int ZERO = 0;
    
    private final IAuthorizationService authService = new AuthorizationServiceImpl();
    private final IAlbumService albumService = new AlbumServiceImpl();
    private final IPlaylistService playlistService = new PlaylistServiceImpl();
    private final ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    public void showAuthMenu() {
        boolean exit = false;
        while (!exit) {
            String[] choice = InputScanner.getStringInput();
            switch (choice[ZERO]) {
                case "auth":
                    if (authorizeApplication()) {
                        showMainMenu();
                    }
                    break;
                case "exit":
                    showGoodbyeMessage();
                    System.exit(0);
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
            switch (choice[ZERO]) {
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
                        showCategorizedPlaylists(choice[ONE]);
                    } else {
                        System.out.println(ConsoleOutput.ADD_CATEGORY_NAME);
                    }
                    break;
                case "exit":
                    showGoodbyeMessage();
                    System.exit(0);
                    break;
                default:
                    System.out.println(ConsoleOutput.VALUE_NOT_SUPPORTED);
            }
        }
    }

    @Override
    public void showNewReleases() {
        System.out.println(ConsoleOutput.NEW_RELEASES);
        albumService.getAll().forEach(
                album -> System.out.println(album.getName() + " " + album.getArtists())
        );
    }
    
    @Override
    public void showFeaturedPlayLists() {
        System.out.println(ConsoleOutput.FEATURED);
        playlistService.getAll().forEach(
              playlist -> System.out.println(playlist.getName())  
        );
    }
    
    @Override
    public void showCategories() {
        System.out.println(ConsoleOutput.CATEGORIES);
        categoryService.getAll().forEach(
                category -> System.out.println(category.getName())
        );
    }

    @Override
    public void showCategorizedPlaylists(String categoryName) {
        ConsoleOutput consoleOutput = new ConsoleOutput(categoryName);
        System.out.println(consoleOutput.PLAYLISTS);
        
        playlistService.getAllByCategoryName(categoryName).forEach(
                category -> System.out.println(category.getName())
        );
    }

    @Override
    public void showGoodbyeMessage() {
        System.out.println(ConsoleOutput.GOODBYE);
    }

    private boolean authorizeApplication() {
        try {
            authService.printAuthURL();
            String authCode = authService.getAuthCode(LocalhostServer.initAndStart());
            HttpRequest authRequest = authService.createAuthReq(authCode);
            String accessToken = authService.getAccessToken(authRequest);
            
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

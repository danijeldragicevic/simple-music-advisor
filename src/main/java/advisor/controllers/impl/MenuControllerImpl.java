package advisor.controllers.impl;

import advisor.controllers.IMenuController;
import advisor.models.Category;
import advisor.services.IAlbumService;
import advisor.services.IAuthService;
import advisor.services.ICategoryService;
import advisor.services.IPlaylistService;
import advisor.services.impl.AlbumServiceImpl;
import advisor.services.impl.AuthServiceImpl;
import advisor.services.impl.CategoryServiceImpl;
import advisor.services.impl.PlaylistServiceImpl;
import advisor.utils.InputScanner;

import java.util.List;

public class MenuControllerImpl implements IMenuController {
    private static final int FIRST_INPUT_VALUE = 0;
    private static final int SECOND_INPUT_VALUE = 1;
    private static final int LENGTH_ONE = 1;
    private static final int STATUS_ZERO = 0;
    
    private final IAuthService authService = new AuthServiceImpl();
    private final IAlbumService albumService = new AlbumServiceImpl();
    private final IPlaylistService playlistService = new PlaylistServiceImpl();
    private final ICategoryService categoryService = new CategoryServiceImpl();

    private static String accessToken;
    
    @Override
    public void showAuthMenu() {
        while (true) {
            String[] choice = InputScanner.getStringInput();
            switch (choice[FIRST_INPUT_VALUE]) {
                case "auth":
                    try {
                        accessToken = authService.getAccessToken();
                    } catch (RuntimeException e) {
                        System.out.println("Something went wrong. Please try again.");
                        break;
                    }
                    showMainMenu();
                    break;
                case "exit":
                    exitTheApplication();
                default:
                    System.out.println("Please, provide access for application.");
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
                    showFeaturedPlaylists();
                    break;
                case "categories":
                    showCategories();
                    break;
                case "playlists":
                    if (choice.length > LENGTH_ONE) {
                        showCategorizedPlaylists(choice[SECOND_INPUT_VALUE]);
                    } else {
                        System.out.println("Need to add category name also. Please try again.");
                    }
                    break;
                case "exit":
                    exitTheApplication();
                default:
                    System.out.println("Such value is not supported.");
            }
        }
    }

    @Override
    public void showNewReleases() {
        albumService.getAlbums(accessToken)
                .forEach(System.out::println);
    }
    
    @Override
    public void showFeaturedPlaylists() {
        playlistService.getPlaylists(accessToken)
                .forEach(System.out::println);
    }
    
    @Override
    public void showCategories() {
        categoryService.getCategories(accessToken)
                .forEach(System.out::println);
    }

    @Override
    public void showCategorizedPlaylists(String cName) {
        List<Category> categories = categoryService.getCategories(accessToken);
        if (categories.stream().anyMatch(category -> category.getName().equalsIgnoreCase(cName))) {
            playlistService.getPlaylistsByCategoryName(accessToken, cName)
                        .forEach(System.out::println);
        } else {
            System.out.println("Unknown category name.");
        }
    }

    @Override
    public void exitTheApplication() {
        System.exit(STATUS_ZERO);
    }
}

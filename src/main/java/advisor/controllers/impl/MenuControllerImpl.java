package advisor.controllers.impl;

import advisor.controllers.IMenuController;
import advisor.repositories.IPlaylistRepository;
import advisor.repositories.impl.PlaylistRepositoryImpl;
import advisor.services.IAlbumService;
import advisor.services.IAuthService;
import advisor.services.ICategoryService;
import advisor.services.impl.AlbumServiceImpl;
import advisor.services.impl.AuthServiceImpl;
import advisor.services.impl.CategoryServiceImpl;
import advisor.utils.ConsoleOutput;
import advisor.utils.InputScanner;

public class MenuControllerImpl implements IMenuController {
    private static final int FIRST_INPUT_VALUE = 0;
    private static final int SECOND_INPUT_VALUE = 1;
    private static final int LENGTH_ONE = 1;
    private static final int STATUS_ZERO = 0;
    
    private final IAuthService authService = new AuthServiceImpl();
    private final IAlbumService albumService = new AlbumServiceImpl();
    private final IPlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
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
                    showGoodbyeMessage();
                    break;
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
                    showFeaturedPlayLists();
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
                    showGoodbyeMessage();
                    break;
                default:
                    System.out.println("Such value is not supported.");
            }
        }
    }

    @Override
    public void showNewReleases() {
        albumService.getNewReleases(accessToken).forEach(
                album -> System.out.println(album)
        );
    }
    
    @Override
    public void showFeaturedPlayLists() {
        playlistRepository.getAll().forEach(
              playlist -> System.out.println(playlist.getName())  
        );
    }
    
    @Override
    public void showCategories() {
        categoryService.getAllCategories(accessToken).forEach(
                category -> System.out.println(category)
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
        System.exit(STATUS_ZERO);
    }
}

package advisor.controllers.impl;

import advisor.controllers.IMenuController;
import advisor.models.Category;
import advisor.models.OutputPage;
import advisor.services.IAlbumService;
import advisor.services.IAuthService;
import advisor.services.ICategoryService;
import advisor.services.IPlaylistService;
import advisor.services.impl.*;
import advisor.utils.InputScanner;

import java.util.List;

public class MenuControllerImpl implements IMenuController {
    private static final int FIRST_INPUT_VALUE = 0;
    private static final int SECOND_INPUT_VALUE = 1;
    private static final int LENGTH_ONE = 1;
    private static final int STATUS_ZERO = 0;
    
    private final IAuthService authService = new AuthServiceImpl();
    private final OutputPageServiceImpl outputPageService = new OutputPageServiceImpl();
    
    // Working on this....
    private final IAlbumService albumService = new AlbumServiceImpl();
    private final IPlaylistService playlistService = new PlaylistServiceImpl();
    private final ICategoryService categoryService = new CategoryServiceImpl();
    // Working on this....
    

    private static String accessToken;
    private static OutputPage outputPage;

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
                    outputPage = showNewReleases();
                    printOutput(outputPage);
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
                    
                case "next":
                    if (outputPage.getNextPageUrl().equals("null")) {
                        System.out.println("No more pages.");
                    } else {
                        outputPage = showNextPage(outputPage.getNextPageUrl(), outputPage.getCurrentPage());
                        printOutput(outputPage);
                    }
                    break;
                    
                case "prev":
                    if (outputPage.getPreviousPageUrl().equals("null")) {
                        System.out.println("No more pages.");
                    } else {
                        outputPage = showPreviousPage(outputPage.getPreviousPageUrl(), outputPage.getCurrentPage());
                        printOutput(outputPage);
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
    public OutputPage showNewReleases() {
        return outputPageService.getAlbums(accessToken);
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
    public OutputPage showNextPage(String nextPageUrl, int currentPage) {
        return outputPageService.showNextPage(accessToken, nextPageUrl, currentPage);
    }
    
    @Override
    public OutputPage showPreviousPage(String previousPageUrl, int currentPage) {
        return outputPageService.showPreviousPage(accessToken, previousPageUrl, currentPage);
    }

    @Override
    public void printOutput(OutputPage outputPage) {
        outputPage.getItems().forEach(System.out::println);
        System.out.printf("---Page %d OF %d---\n", outputPage.getCurrentPage(), outputPage.getTotalPagesToDisplay());
    }

    @Override
    public void exitTheApplication() {
        System.exit(STATUS_ZERO);
    }
}

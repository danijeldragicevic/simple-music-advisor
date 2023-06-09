package advisor.controllers.impl;

import advisor.config.ExternalApiConfig;
import advisor.controllers.IMenuController;
import advisor.models.OutputPage;
import advisor.services.IAuthService;
import advisor.services.IPageService;
import advisor.services.IPlaylistService;
import advisor.services.impl.AuthServiceImpl;
import advisor.services.impl.PageServiceImpl;
import advisor.services.impl.PlaylistServiceImpl;
import advisor.utils.InputScanner;

import static advisor.config.ResourceNames.ALBUMS;
import static advisor.config.ResourceNames.CATEGORIES;

public class MenuControllerImpl implements IMenuController {
    private static final int FIRST_INPUT_VALUE = 0;
    private static final int SECOND_INPUT_VALUE = 1;
    private static final int LENGTH_ONE = 1;
    private static final int STATUS_ZERO = 0;
    
    private final IAuthService authService = new AuthServiceImpl();
    private IPageService pageService;
    
    // Working on this....
    private final IPlaylistService playlistService = new PlaylistServiceImpl();
    // Working on this....
    

    private static String accessToken;
    private static OutputPage outputPage = new OutputPage();

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
                    pageService = new PageServiceImpl(ALBUMS.getName());
                    outputPage = pageService.createPage(accessToken, ExternalApiConfig.NEW_RELEASES_PAGINATED_PATH, outputPage.getCurrentPage());
                    printOutput(outputPage);
                    break;
                case "featured":
                    showFeaturedPlaylists();
                    break;
                case "categories":
                    pageService = new PageServiceImpl(CATEGORIES.getName());
                    outputPage = pageService.createPage(accessToken, ExternalApiConfig.CATEGORIES_PAGINATED_PATH, outputPage.getCurrentPage());
                    printOutput(outputPage);
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
                        outputPage = pageService.createPage(accessToken, outputPage.getNextPageUrl(), outputPage.getCurrentPage() + 1);
                        printOutput(outputPage);
                    }
                    break;
                case "prev":
                    if (outputPage.getPreviousPageUrl().equals("null")) {
                        System.out.println("No more pages.");
                    } else {
                        outputPage = pageService.createPage(accessToken, outputPage.getPreviousPageUrl(), outputPage.getCurrentPage() - 1);
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
    public void showFeaturedPlaylists() {
        playlistService.getPlaylists(accessToken)
                .forEach(System.out::println);
    }
    
    @Override
    public void showCategorizedPlaylists(String cName) {
//        List<Category> categories = categoryPageService.getCategoriesPage(accessToken);
//        if (categories.stream().anyMatch(category -> category.getName().equalsIgnoreCase(cName))) {
//            playlistService.getPlaylistsByCategoryName(accessToken, cName)
//                        .forEach(System.out::println);
//        } else {
//            System.out.println("Unknown category name.");
//        }
        
    }
    
    @Override
    public void printOutput(OutputPage outputPage) {
        outputPage.getPageItems().forEach(System.out::println);
        System.out.printf("---Page %d OF %d---\n", outputPage.getCurrentPage(), outputPage.getTotalPagesToDisplay());
    }

    @Override
    public void exitTheApplication() {
        System.exit(STATUS_ZERO);
    }
}

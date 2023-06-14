package advisor.controllers.impl;

import advisor.config.ExternalApiConfig;
import advisor.controllers.IMenuController;
import advisor.models.OutputPage;
import advisor.services.IAuthService;
import advisor.services.IPageService;
import advisor.services.impl.AuthServiceImpl;
import advisor.services.impl.PageServiceImpl;
import advisor.utils.InputScanner;

import static advisor.config.ResourceNames.*;

public class MenuControllerImpl implements IMenuController {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    
    private static String accessToken;
    private static OutputPage outputPage = new OutputPage();
    
    private final IAuthService authService = new AuthServiceImpl();
    private IPageService pageService;

    @Override
    public void showAuthMenu() {
        while (true) {
            String[] choice = InputScanner.getStringInput();
            switch (choice[ZERO]) {
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
            switch (choice[ZERO]) {
                case "new":
                    pageService = new PageServiceImpl(ALBUMS.getName());
                    outputPage.setCurrentPage(ONE);
                    outputPage = pageService.createPage(accessToken, ExternalApiConfig.NEW_RELEASES_PATH, outputPage.getCurrentPage());
                    printOutput(outputPage);
                    break;
                case "featured":
                    pageService = new PageServiceImpl(PLAYLISTS.getName());
                    outputPage.setCurrentPage(ONE);
                    outputPage = pageService.createPage(accessToken, ExternalApiConfig.FEATURED_PLAYLISTS_PATH, outputPage.getCurrentPage());
                    printOutput(outputPage);
                    break;
                case "categories":
                    pageService = new PageServiceImpl(CATEGORIES.getName());
                    outputPage.setCurrentPage(ONE);
                    outputPage = pageService.createPage(accessToken, ExternalApiConfig.CATEGORIES_PATH, outputPage.getCurrentPage());
                    printOutput(outputPage);
                    break;
                case "playlists":
                    if (choice.length > ONE) {
                        showCategorizedPlaylists(choice[ONE]);
                    } else {
                        System.out.println("Need to add category name also. Please try again.");
                    }
                    break;
                case "next":
                    if (outputPage.getNextPageUrl().equals("null")) {
                        System.out.println("No more pages.");
                    } else {
                        outputPage = pageService.createPage(accessToken, outputPage.getNextPageUrl(), outputPage.getCurrentPage() + ONE);
                        printOutput(outputPage);
                    }
                    break;
                case "prev":
                    if (outputPage.getPreviousPageUrl().equals("null")) {
                        System.out.println("No more pages.");
                    } else {
                        outputPage = pageService.createPage(accessToken, outputPage.getPreviousPageUrl(), outputPage.getCurrentPage() - ONE);
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
        System.exit(ZERO);
    }
}

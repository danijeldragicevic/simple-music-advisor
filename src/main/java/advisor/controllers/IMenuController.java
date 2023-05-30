package advisor.controllers;

import java.io.IOException;

public interface IMenuController {
    void showAuthMenu();
    void authenticateApp() throws IOException;
    void showMainMenu();
    void showNewReleases();
    void showFeaturedPlayLists();
    void showCategories();
    void showCategorizedPlaylists(String categoryName);
    void showGoodbyeMessage();
}

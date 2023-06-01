package advisor.controllers;

public interface IMenuController {
    void showAuthMenu();
    void showMainMenu();
    void showNewReleases();
    void showFeaturedPlaylists();
    void showCategories();
    void showCategorizedPlaylists(String categoryName);
    void exitTheApplication();
}

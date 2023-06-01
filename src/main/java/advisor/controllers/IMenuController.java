package advisor.controllers;

public interface IMenuController {
    void showAuthMenu();
    void showMainMenu();
    void showNewReleases();
    void showFeaturedPlayLists();
    void showCategories();
    void showCategorizedPlaylists(String categoryName);
    void showGoodbyeMessage();
}

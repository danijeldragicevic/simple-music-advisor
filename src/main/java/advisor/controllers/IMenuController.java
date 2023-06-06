package advisor.controllers;

import advisor.models.OutputPage;

public interface IMenuController {
    void showAuthMenu();
    void showMainMenu();
    OutputPage showNewReleases();
    void showFeaturedPlaylists();
    void showCategories();
    void showCategorizedPlaylists(String cName);
    OutputPage showNextPage(String nextPageUrl, int currentPage);
    OutputPage showPreviousPage(String previousPageUrl, int currentPage);
    void printOutput(OutputPage outputPage);
    void exitTheApplication();
    
}
